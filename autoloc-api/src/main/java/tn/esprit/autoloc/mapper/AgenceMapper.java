package tn.esprit.autoloc.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.dto.request.AgenceRequest;
import tn.esprit.autoloc.dto.response.AgenceResponse;

/** Conversion entre l'entite Agence et ses DTO (request / response). */
@Component
public class AgenceMapper {

    /** Request -> nouvelle entite. Les relations sont deja chargees par le service. */
    public Agence toEntity(AgenceRequest request) {
        Agence agence = new Agence();
        apply(agence, request);
        return agence;
    }

    /** Recopie les champs modifiables sur une entite existante (l'identifiant n'est jamais touche). */
    public void updateEntity(Agence agence, AgenceRequest request) {
        apply(agence, request);
    }

    /** Entite -> response (aucune entite n'est exposee au client). */
    public AgenceResponse toResponse(Agence agence) {
        return new AgenceResponse(
                agence.getIdAgence(),
                agence.getNom(),
                agence.getVille(),
                agence.getAdresse(),
                agence.getTelephone());
    }

    private void apply(Agence agence, AgenceRequest request) {
        agence.setNom(request.nom());
        agence.setVille(request.ville());
        agence.setAdresse(request.adresse());
        agence.setTelephone(request.telephone());
    }
}
