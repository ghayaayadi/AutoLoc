package tn.esprit.autoloc.dto.response;

import java.math.BigDecimal;
import java.util.Set;
import tn.esprit.autoloc.domain.CategorieVehicule;
import tn.esprit.autoloc.domain.StatutVehicule;

/** Donnees renvoyees au client pour un vehicule. */
public record VehiculeResponse(
        Long idVehicule,
        String immatriculation,
        String marque,
        String modele,
        CategorieVehicule categorie,
        BigDecimal tarifJournalier,
        StatutVehicule statut,
        Long agenceId,
        String agenceNom,
        Set<Long> equipementIds) {
}
