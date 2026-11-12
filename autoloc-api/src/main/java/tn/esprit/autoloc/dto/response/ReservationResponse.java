package tn.esprit.autoloc.dto.response;

import java.time.LocalDate;
import tn.esprit.autoloc.domain.StatutReservation;

/** Donnees renvoyees au client pour une reservation. */
public record ReservationResponse(
        Long idReservation,
        LocalDate dateDebut,
        LocalDate dateFin,
        StatutReservation statut,
        Long vehiculeId,
        String vehiculeImmatriculation,
        Long clientId,
        String clientNomComplet) {
}
