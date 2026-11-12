package tn.esprit.autoloc.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/** Donnees recues du client pour creer ou modifier un equipement (sans identifiant). */
public record EquipementRequest(
        @NotBlank(message = "Le libelle est obligatoire")
        @Size(max = 100, message = "Le libelle ne doit pas depasser 100 caracteres")
        String libelle) {
}
