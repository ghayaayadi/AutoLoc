package tn.esprit.autoloc.mapper;

import java.util.Comparator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.dto.response.ContratDTO;
import tn.esprit.autoloc.dto.response.ReservationResponseDTO;
import tn.esprit.autoloc.dto.response.VehiculeDTO;

/** Conversion entite -> DTO pour les services d'affectation (aucune entite n'est exposee). */
@Component
@RequiredArgsConstructor
public class AffectationMapper {

    private final EquipementMapper equipementMapper;
    private final MaintenanceMapper maintenanceMapper;

    public ContratDTO toContratDTO(Contrat contrat) {
        Reservation reservation = contrat.getReservation();
        return new ContratDTO(
                contrat.getIdContrat(),
                contrat.getDateSignature(),
                contrat.getMontantTotal(),
                contrat.isValide(),
                reservation == null ? null : reservation.getIdReservation(),
                reservation == null ? null : reservation.getDateDebut(),
                reservation == null ? null : reservation.getDateFin());
    }

    public ReservationResponseDTO toReservationDTO(Reservation reservation) {
        Vehicule vehicule = reservation.getVehicule();
        return new ReservationResponseDTO(
                reservation.getIdReservation(),
                reservation.getDateDebut(),
                reservation.getDateFin(),
                reservation.getStatut(),
                vehicule == null ? null : vehicule.getIdVehicule(),
                vehicule == null ? null : vehicule.getImmatriculation(),
                reservation.getClient() == null ? null : reservation.getClient().getIdClient(),
                reservation.getClient() == null ? null
                        : reservation.getClient().getNom() + " " + reservation.getClient().getPrenom(),
                reservation.getContrat() == null ? null : toContratDTO(reservation.getContrat()));
    }

    public VehiculeDTO toVehiculeDTO(Vehicule vehicule) {
        return new VehiculeDTO(
                vehicule.getIdVehicule(),
                vehicule.getImmatriculation(),
                vehicule.getMarque(),
                vehicule.getModele(),
                vehicule.getCategorie(),
                vehicule.getTarifJournalier(),
                vehicule.getStatut(),
                vehicule.getAgence() == null ? null : vehicule.getAgence().getIdAgence(),
                vehicule.getAgence() == null ? null : vehicule.getAgence().getNom(),
                vehicule.getEquipements().stream()
                        .sorted(Comparator.comparing(Equipement::getIdEquipement))
                        .map(equipementMapper::toResponse)
                        .toList(),
                vehicule.getMaintenances().stream()
                        .sorted(Comparator.comparing(m -> m.getIdMaintenance()))
                        .map(maintenanceMapper::toResponse)
                        .toList());
    }
}
