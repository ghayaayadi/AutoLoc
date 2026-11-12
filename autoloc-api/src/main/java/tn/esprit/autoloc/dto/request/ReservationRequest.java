package tn.esprit.autoloc.dto.request;

import java.time.LocalDate;
import jakarta.validation.constraints.NotNull;
import tn.esprit.autoloc.domain.StatutReservation;

/** Donnees recues du client pour creer ou modifier une reservation (sans identifiant). */
public record ReservationRequest(
        @NotNull(message = "La date de debut est obligatoire") LocalDate dateDebut,
        @NotNull(message = "La date de fin est obligatoire") LocalDate dateFin,
        @NotNull(message = "Le statut est obligatoire") StatutReservation statut,
        @NotNull(message = "L'identifiant de vehicule est obligatoire")
        Long vehiculeId,
        @NotNull(message = "L'identifiant de client est obligatoire")
        Long clientId) {
}
