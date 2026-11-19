package tn.esprit.autoloc.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.domain.StatutReservation;
import tn.esprit.autoloc.domain.StatutVehicule;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.dto.request.ReservationWithContratRequestDTO;
import tn.esprit.autoloc.dto.response.ContratDTO;
import tn.esprit.autoloc.dto.response.ReservationResponseDTO;
import tn.esprit.autoloc.dto.response.VehiculeDTO;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.AffectationMapper;
import tn.esprit.autoloc.repository.IClientRepository;
import tn.esprit.autoloc.repository.IContratRepository;
import tn.esprit.autoloc.repository.IEquipementRepository;
import tn.esprit.autoloc.repository.IMaintenanceRepository;
import tn.esprit.autoloc.repository.IReservationRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;
import tn.esprit.autoloc.service.IAffectationService;

/**
 * Services d'affectation. Un service dedie (et non une methode dans chaque service d'entite)
 * evite les dependances croisees entre services (cf. cycle de l'Atelier 4) : il ne depend que des repositories.
 */
@Service
@RequiredArgsConstructor
public class AffectationServiceImpl implements IAffectationService {

    private static final int DESCRIPTION_MAX = 255;

    private final IReservationRepository reservationRepository;
    private final IClientRepository clientRepository;
    private final IVehiculeRepository vehiculeRepository;
    private final IEquipementRepository equipementRepository;
    private final IMaintenanceRepository maintenanceRepository;
    private final IContratRepository contratRepository;
    private final AffectationMapper affectationMapper;

    /** Reservation -> Client : le client est retrouve par son nom et son prenom. */
    @Override
    @Transactional
    public ReservationResponseDTO affecterReservationAClient(Long idReservation, String nom, String prenom) {
        exigerTexte(nom, "Le nom");
        exigerTexte(prenom, "Le prenom");
        Reservation reservation = chargerReservation(idReservation);
        if (reservation.getStatut() == StatutReservation.ANNULEE
                || reservation.getStatut() == StatutReservation.TERMINEE) {
            throw new IllegalArgumentException(
                    "Impossible de modifier le client d'une reservation " + reservation.getStatut());
        }
        Client client = chargerClientParNomPrenom(nom.trim(), prenom.trim());
        reservation.setClient(client);
        return affectationMapper.toReservationDTO(reservationRepository.save(reservation));
    }

    /** Vehicule <-> Equipement (ManyToMany, cote proprietaire = Vehicule). Operation idempotente. */
    @Override
    @Transactional
    public VehiculeDTO affecterVehiculeAEquipement(Long idVehicule, Long idEquipement) {
        Vehicule vehicule = chargerVehicule(idVehicule);
        Equipement equipement = equipementRepository.findById(idEquipement)
                .orElseThrow(() -> new ResourceNotFoundException("Equipement", idEquipement));
        vehicule.getEquipements().add(equipement);
        equipement.getVehicules().add(vehicule);
        return affectationMapper.toVehiculeDTO(vehiculeRepository.save(vehicule));
    }

    /**
     * Maintenance -> Vehicule (retrouve par son immatriculation). Les dates ne font pas partie de la
     * signature : la maintenance commence aujourd'hui et, dateFin etant obligatoire dans le modele,
     * elle est initialisee au meme jour. Le vehicule passe au statut MAINTENANCE.
     */
    @Override
    @Transactional
    public VehiculeDTO affecterMaintenanceAvehicule(String description, String immatriculation) {
        exigerTexte(description, "La description");
        exigerTexte(immatriculation, "L'immatriculation");
        if (description.length() > DESCRIPTION_MAX) {
            throw new IllegalArgumentException(
                    "La description ne doit pas depasser " + DESCRIPTION_MAX + " caracteres");
        }
        String immat = immatriculation.trim();
        Vehicule vehicule = vehiculeRepository.findByImmatriculation(immat)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Vehicule introuvable avec l'immatriculation " + immat));
        if (vehicule.getStatut() == StatutVehicule.LOUE) {
            throw new IllegalArgumentException(
                    "Le vehicule " + immat + " est actuellement loue : maintenance impossible");
        }
        LocalDate aujourdhui = LocalDate.now();
        Maintenance maintenance = new Maintenance();
        maintenance.setDescription(description.trim());
        maintenance.setDateDebut(aujourdhui);
        maintenance.setDateFin(aujourdhui);
        maintenance.setVehicule(vehicule);
        maintenanceRepository.save(maintenance);

        vehicule.getMaintenances().add(maintenance);
        vehicule.setStatut(StatutVehicule.MAINTENANCE);
        return affectationMapper.toVehiculeDTO(vehiculeRepository.save(vehicule));
    }

    /**
     * Contrat -> Reservation : la reservation est identifiee par sa periode (dateDebut, dateFin) et ne doit
     * pas avoir de contrat. Montant = tarif journalier du vehicule x nombre de jours (minimum 1).
     * Une reservation EN_ATTENTE devient CONFIRMEE a la signature.
     */
    @Override
    @Transactional
    public ContratDTO affecterContratAReservation(LocalDate dateSignature, LocalDate dateDebut, LocalDate dateFin) {
        exigerDate(dateSignature, "La date de signature");
        validerPeriode(dateDebut, dateFin);

        List<Reservation> candidates = reservationRepository.findSansContratByPeriode(dateDebut, dateFin).stream()
                .filter(r -> r.getStatut() != StatutReservation.ANNULEE
                        && r.getStatut() != StatutReservation.TERMINEE)
                .toList();
        if (candidates.isEmpty()) {
            throw new ResourceNotFoundException("Aucune reservation sans contrat trouvee du "
                    + dateDebut + " au " + dateFin);
        }
        if (candidates.size() > 1) {
            throw new IllegalArgumentException("Plusieurs reservations sans contrat correspondent a la periode du "
                    + dateDebut + " au " + dateFin + " : precisez la reservation");
        }
        Reservation reservation = candidates.get(0);
        Contrat contrat = creerContrat(reservation, dateSignature);
        return affectationMapper.toContratDTO(contrat);
    }

    /** Cree la reservation (CONFIRMEE) et son contrat dans la meme transaction : tout ou rien. */
    @Override
    @Transactional
    public ReservationResponseDTO creerReservationEtContratAssocie(ReservationWithContratRequestDTO requestDTO) {
        if (requestDTO == null) {
            throw new IllegalArgumentException("La requete est obligatoire");
        }
        validerPeriode(requestDTO.dateDebut(), requestDTO.dateFin());
        exigerDate(requestDTO.dateSignature(), "La date de signature");
        Vehicule vehicule = chargerVehicule(requestDTO.vehiculeId());
        Client client = clientRepository.findById(requestDTO.clientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client", requestDTO.clientId()));
        if (vehicule.getStatut() == StatutVehicule.MAINTENANCE) {
            throw new IllegalArgumentException(
                    "Le vehicule " + vehicule.getImmatriculation() + " est en maintenance : reservation impossible");
        }

        Reservation reservation = new Reservation();
        reservation.setDateDebut(requestDTO.dateDebut());
        reservation.setDateFin(requestDTO.dateFin());
        reservation.setStatut(StatutReservation.CONFIRMEE);
        reservation.setVehicule(vehicule);
        reservation.setClient(client);
        reservationRepository.save(reservation);

        creerContrat(reservation, requestDTO.dateSignature());
        return affectationMapper.toReservationDTO(reservation);
    }

    // ---------- methodes privees ----------

    private Contrat creerContrat(Reservation reservation, LocalDate dateSignature) {
        Contrat contrat = new Contrat();
        contrat.setDateSignature(dateSignature);
        contrat.setMontantTotal(calculerMontant(reservation));
        contrat.setValide(true);
        contrat.setReservation(reservation);
        contratRepository.save(contrat);

        reservation.setContrat(contrat);
        if (reservation.getStatut() == StatutReservation.EN_ATTENTE) {
            reservation.setStatut(StatutReservation.CONFIRMEE);
        }
        return contrat;
    }

    private BigDecimal calculerMontant(Reservation reservation) {
        long jours = Math.max(1, ChronoUnit.DAYS.between(reservation.getDateDebut(), reservation.getDateFin()));
        return reservation.getVehicule().getTarifJournalier().multiply(BigDecimal.valueOf(jours));
    }

    private Reservation chargerReservation(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", id));
    }

    private Vehicule chargerVehicule(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", id));
    }

    private Client chargerClientParNomPrenom(String nom, String prenom) {
        List<Client> clients = clientRepository.findByNomIgnoreCaseAndPrenomIgnoreCase(nom, prenom);
        if (clients.isEmpty()) {
            throw new ResourceNotFoundException("Client introuvable : " + nom + " " + prenom);
        }
        if (clients.size() > 1) {
            throw new IllegalArgumentException("Plusieurs clients portent le nom " + nom + " " + prenom
                    + " : affectation ambigue");
        }
        return clients.get(0);
    }

    private void validerPeriode(LocalDate dateDebut, LocalDate dateFin) {
        exigerDate(dateDebut, "La date de debut");
        exigerDate(dateFin, "La date de fin");
        if (dateFin.isBefore(dateDebut)) {
            throw new IllegalArgumentException("La date de fin est anterieure a la date de debut");
        }
    }

    private void exigerDate(LocalDate date, String libelle) {
        if (date == null) {
            throw new IllegalArgumentException(libelle + " est obligatoire");
        }
    }

    private void exigerTexte(String valeur, String libelle) {
        if (valeur == null || valeur.isBlank()) {
            throw new IllegalArgumentException(libelle + " est obligatoire");
        }
    }
}
