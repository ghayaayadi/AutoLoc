package tn.esprit.autoloc.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

/** Contrat renvoye par les services d'affectation (avec la periode de la reservation associee). */
public record ContratDTO(
        Long idContrat,
        LocalDate dateSignature,
        BigDecimal montantTotal,
        Boolean valide,
        Long reservationId,
        LocalDate dateDebut,
        LocalDate dateFin) {
}
