package tn.esprit.autoloc.dto.response;

import java.math.BigDecimal;
import java.util.List;
import tn.esprit.autoloc.domain.CategorieVehicule;
import tn.esprit.autoloc.domain.StatutVehicule;

/** Vehicule renvoye par les services d'affectation, avec ses equipements et ses maintenances. */
public record VehiculeDTO(
        Long idVehicule,
        String immatriculation,
        String marque,
        String modele,
        CategorieVehicule categorie,
        BigDecimal tarifJournalier,
        StatutVehicule statut,
        Long agenceId,
        String agenceNom,
        List<EquipementResponse> equipements,
        List<MaintenanceResponse> maintenances) {
}
