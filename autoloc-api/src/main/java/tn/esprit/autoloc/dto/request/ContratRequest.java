package tn.esprit.autoloc.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

/** Donnees recues du client pour creer ou modifier un contrat (sans identifiant). */
public record ContratRequest(
        @NotNull(message = "La date de signature est obligatoire") LocalDate dateSignature,
        @NotNull(message = "Le montant total est obligatoire")
        @DecimalMin(value = "0.0", message = "Le montant total ne doit pas etre negatif")
        @Digits(integer = 8, fraction = 2, message = "Le montant total accepte 8 chiffres et 2 decimales au maximum")
        BigDecimal montantTotal,
        @NotNull(message = "L'indicateur valide est obligatoire") Boolean valide,
        @NotNull(message = "L'identifiant de reservation est obligatoire")
        Long reservationId) {
}
