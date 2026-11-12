package tn.esprit.autoloc.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.dto.request.ContratRequest;
import tn.esprit.autoloc.dto.response.ContratResponse;

/** Conversion entre l'entite Contrat et ses DTO (request / response). */
@Component
public class ContratMapper {

    /** Request -> nouvelle entite. Les relations sont deja chargees par le service. */
    public Contrat toEntity(ContratRequest request, Reservation reservation) {
        Contrat contrat = new Contrat();
        apply(contrat, request, reservation);
        return contrat;
    }

    /** Recopie les champs modifiables sur une entite existante (l'identifiant n'est jamais touche). */
    public void updateEntity(Contrat contrat, ContratRequest request, Reservation reservation) {
        apply(contrat, request, reservation);
    }

    /** Entite -> response (aucune entite n'est exposee au client). */
    public ContratResponse toResponse(Contrat contrat) {
        return new ContratResponse(
                contrat.getIdContrat(),
                contrat.getDateSignature(),
                contrat.getMontantTotal(),
                contrat.isValide(),
                contrat.getReservation() == null ? null : contrat.getReservation().getIdReservation());
    }

    private void apply(Contrat contrat, ContratRequest request, Reservation reservation) {
        contrat.setDateSignature(request.dateSignature());
        contrat.setMontantTotal(request.montantTotal());
        contrat.setValide(request.valide());
        contrat.setReservation(reservation);
    }
}
