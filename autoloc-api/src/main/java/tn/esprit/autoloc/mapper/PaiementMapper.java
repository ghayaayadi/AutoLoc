package tn.esprit.autoloc.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.dto.response.PaiementResponse;

@Component
public class PaiementMapper {

    public PaiementResponse toResponse(Paiement paiement) {
        return new PaiementResponse(
                paiement.getIdPaiement(),
                paiement.getContrat() == null ? null : paiement.getContrat().getIdContrat(),
                paiement.getMontant(),
                paiement.getDatePaiement(),
                paiement.getModePaiement());
    }
}
