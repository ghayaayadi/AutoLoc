package tn.esprit.autoloc.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import tn.esprit.autoloc.domain.ModePaiement;

/** Donnees renvoyees au client pour un paiement (lecture seule). */
public record PaiementResponse(
        Long idPaiement,
        Long contratId,
        BigDecimal montant,
        LocalDate datePaiement,
        ModePaiement modePaiement) {
}
