package tn.esprit.autoloc.dto.request;

import java.time.LocalDate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/** Donnees recues du client pour creer ou modifier un client (sans identifiant). */
public record ClientRequest(
        @NotBlank(message = "Le nom est obligatoire")
        @Size(max = 50, message = "Le nom ne doit pas depasser 50 caracteres")
        String nom,
        @NotBlank(message = "Le prenom est obligatoire")
        @Size(max = 50, message = "Le prenom ne doit pas depasser 50 caracteres")
        String prenom,
        @NotBlank(message = "L'email est obligatoire")
        @Email(message = "Le format de l'email est invalide")
        @Size(max = 100, message = "L'email ne doit pas depasser 100 caracteres")
        String email,
        @NotBlank(message = "Le telephone est obligatoire")
        @Pattern(regexp = "^\\+?[0-9 ]{8,20}$", message = "Le telephone doit contenir 8 a 20 chiffres (indicatif + autorise)")
        String telephone,
        @NotBlank(message = "Le numero de permis est obligatoire")
        @Size(max = 30, message = "Le numero de permis ne doit pas depasser 30 caracteres")
        String numPermis,
        @NotNull(message = "La date d'inscription est obligatoire")
        @PastOrPresent(message = "La date d'inscription ne peut pas etre dans le futur")
        LocalDate dateInscription) {
}
