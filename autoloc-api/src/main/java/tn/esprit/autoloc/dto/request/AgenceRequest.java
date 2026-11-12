package tn.esprit.autoloc.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/** Donnees recues du client pour creer ou modifier une agence (sans identifiant). */
public record AgenceRequest(
        @NotBlank(message = "Le nom est obligatoire")
        @Size(max = 100, message = "Le nom ne doit pas depasser 100 caracteres")
        String nom,
        @NotBlank(message = "La ville est obligatoire")
        @Size(max = 50, message = "La ville ne doit pas depasser 50 caracteres")
        String ville,
        @NotBlank(message = "L'adresse est obligatoire")
        @Size(max = 150, message = "L'adresse ne doit pas depasser 150 caracteres")
        String adresse,
        @NotBlank(message = "Le telephone est obligatoire")
        @Pattern(regexp = "^\\+?[0-9 ]{8,20}$", message = "Le telephone doit contenir 8 a 20 chiffres (indicatif + autorise)")
        String telephone) {
}
