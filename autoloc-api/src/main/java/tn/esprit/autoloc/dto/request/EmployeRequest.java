package tn.esprit.autoloc.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import tn.esprit.autoloc.domain.RoleEmploye;

/** Donnees recues du client pour creer ou modifier un employe (sans identifiant). */
public record EmployeRequest(
        @NotBlank(message = "Le nom est obligatoire")
        @Size(max = 50, message = "Le nom ne doit pas depasser 50 caracteres")
        String nom,
        @NotBlank(message = "Le prenom est obligatoire")
        @Size(max = 50, message = "Le prenom ne doit pas depasser 50 caracteres")
        String prenom,
        @NotNull(message = "Le role est obligatoire") RoleEmploye role,
        Long agenceId) {
}
