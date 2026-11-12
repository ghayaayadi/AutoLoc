package tn.esprit.autoloc.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

/** Donnees renvoyees au client pour un contrat. */
public record ContratResponse(
        Long idContrat,
        LocalDate dateSignature,
        BigDecimal montantTotal,
        Boolean valide,
        Long reservationId) {
}
