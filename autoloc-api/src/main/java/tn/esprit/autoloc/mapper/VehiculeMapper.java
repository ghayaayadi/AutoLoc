package tn.esprit.autoloc.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.dto.request.VehiculeRequest;
import tn.esprit.autoloc.dto.response.VehiculeResponse;

/** Conversion entre l'entite Vehicule et ses DTO (request / response). */
@Component
public class VehiculeMapper {

    /** Request -> nouvelle entite. Les relations sont deja chargees par le service. */
    public Vehicule toEntity(VehiculeRequest request, Agence agence, Set<Equipement> equipements) {
        Vehicule vehicule = new Vehicule();
        apply(vehicule, request, agence, equipements);
        return vehicule;
    }

    /** Recopie les champs modifiables sur une entite existante (l'identifiant n'est jamais touche). */
    public void updateEntity(Vehicule vehicule, VehiculeRequest request, Agence agence, Set<Equipement> equipements) {
        apply(vehicule, request, agence, equipements);
    }

    /** Entite -> response (aucune entite n'est exposee au client). */
    public VehiculeResponse toResponse(Vehicule vehicule) {
        return new VehiculeResponse(
                vehicule.getIdVehicule(),
                vehicule.getImmatriculation(),
                vehicule.getMarque(),
                vehicule.getModele(),
                vehicule.getCategorie(),
                vehicule.getTarifJournalier(),
                vehicule.getStatut(),
                vehicule.getAgence() == null ? null : vehicule.getAgence().getIdAgence(),
                vehicule.getAgence() == null ? null : vehicule.getAgence().getNom(),
                vehicule.getEquipements().stream().map(Equipement::getIdEquipement).collect(Collectors.toSet()));
    }

    private void apply(Vehicule vehicule, VehiculeRequest request, Agence agence, Set<Equipement> equipements) {
        vehicule.setImmatriculation(request.immatriculation());
        vehicule.setMarque(request.marque());
        vehicule.setModele(request.modele());
        vehicule.setCategorie(request.categorie());
        vehicule.setTarifJournalier(request.tarifJournalier());
        vehicule.setStatut(request.statut());
        vehicule.setAgence(agence);
        vehicule.setEquipements(equipements);
    }
}
