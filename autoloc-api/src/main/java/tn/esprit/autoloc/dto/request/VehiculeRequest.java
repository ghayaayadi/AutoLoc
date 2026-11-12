package tn.esprit.autoloc.dto.request;

import java.math.BigDecimal;
import java.util.Set;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import tn.esprit.autoloc.domain.CategorieVehicule;
import tn.esprit.autoloc.domain.StatutVehicule;

/** Donnees recues du client pour creer ou modifier un vehicule (sans identifiant). */
public record VehiculeRequest(
        @NotBlank(message = "L'immatriculation est obligatoire")
        @Size(max = 20, message = "L'immatriculation ne doit pas depasser 20 caracteres")
        String immatriculation,
        @NotBlank(message = "La marque est obligatoire")
        @Size(max = 50, message = "La marque ne doit pas depasser 50 caracteres")
        String marque,
        @NotBlank(message = "Le modele est obligatoire")
        @Size(max = 50, message = "Le modele ne doit pas depasser 50 caracteres")
        String modele,
        @NotNull(message = "La categorie est obligatoire") CategorieVehicule categorie,
        @NotNull(message = "Le tarif journalier est obligatoire")
        @DecimalMin(value = "0.0", message = "Le tarif journalier ne doit pas etre negatif")
        @Digits(integer = 8, fraction = 2, message = "Le tarif journalier accepte 8 chiffres et 2 decimales au maximum")
        BigDecimal tarifJournalier,
        @NotNull(message = "Le statut est obligatoire") StatutVehicule statut,
        Long agenceId,
        Set<Long> equipementIds) {
}
