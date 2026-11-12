package tn.esprit.autoloc.dto.response;

import java.time.LocalDate;

/** Donnees renvoyees au client pour une maintenance. */
public record MaintenanceResponse(
        Long idMaintenance,
        LocalDate dateDebut,
        LocalDate dateFin,
        String description,
        Long vehiculeId,
        String vehiculeImmatriculation) {
}
