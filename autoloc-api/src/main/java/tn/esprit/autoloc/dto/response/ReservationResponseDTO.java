package tn.esprit.autoloc.dto.response;

import java.time.LocalDate;
import tn.esprit.autoloc.domain.StatutReservation;

/** Reservation renvoyee par les services d'affectation ; le contrat est null s'il n'existe pas encore. */
public record ReservationResponseDTO(
        Long idReservation,
        LocalDate dateDebut,
        LocalDate dateFin,
        StatutReservation statut,
        Long vehiculeId,
        String vehiculeImmatriculation,
        Long clientId,
        String clientNomComplet,
        ContratDTO contrat) {
}
