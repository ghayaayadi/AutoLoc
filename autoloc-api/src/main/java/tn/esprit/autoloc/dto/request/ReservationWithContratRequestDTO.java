package tn.esprit.autoloc.dto.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Donnees recues pour creer, en une seule operation, une reservation et son contrat.
 * Le statut et le montant total ne sont pas fournis : ils sont calcules par le service.
 */
public record ReservationWithContratRequestDTO(
        @NotNull(message = "La date de debut est obligatoire") LocalDate dateDebut,
        @NotNull(message = "La date de fin est obligatoire") LocalDate dateFin,
        @NotNull(message = "L'identifiant de vehicule est obligatoire") Long vehiculeId,
        @NotNull(message = "L'identifiant de client est obligatoire") Long clientId,
        @NotNull(message = "La date de signature est obligatoire") LocalDate dateSignature) {
}
