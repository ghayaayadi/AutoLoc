package tn.esprit.autoloc.dto.request;

import java.time.LocalDate;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/** Donnees recues du client pour creer ou modifier une maintenance (sans identifiant). */
public record MaintenanceRequest(
        @NotNull(message = "La date de debut est obligatoire") LocalDate dateDebut,
        @NotNull(message = "La date de fin est obligatoire") LocalDate dateFin,
        @Size(max = 255, message = "La description ne doit pas depasser 255 caracteres") String description,
        @NotNull(message = "L'identifiant de vehicule est obligatoire")
        Long vehiculeId) {
}
