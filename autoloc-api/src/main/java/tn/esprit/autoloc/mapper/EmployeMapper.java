package tn.esprit.autoloc.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.dto.request.EmployeRequest;
import tn.esprit.autoloc.dto.response.EmployeResponse;

/** Conversion entre l'entite Employe et ses DTO (request / response). */
@Component
public class EmployeMapper {

    /** Request -> nouvelle entite. Les relations sont deja chargees par le service. */
    public Employe toEntity(EmployeRequest request, Agence agence) {
        Employe employe = new Employe();
        apply(employe, request, agence);
        return employe;
    }

    /** Recopie les champs modifiables sur une entite existante (l'identifiant n'est jamais touche). */
    public void updateEntity(Employe employe, EmployeRequest request, Agence agence) {
        apply(employe, request, agence);
    }

    /** Entite -> response (aucune entite n'est exposee au client). */
    public EmployeResponse toResponse(Employe employe) {
        return new EmployeResponse(
                employe.getIdEmploye(),
                employe.getNom(),
                employe.getPrenom(),
                employe.getRole(),
                employe.getAgence() == null ? null : employe.getAgence().getIdAgence(),
                employe.getAgence() == null ? null : employe.getAgence().getNom());
    }

    private void apply(Employe employe, EmployeRequest request, Agence agence) {
        employe.setNom(request.nom());
        employe.setPrenom(request.prenom());
        employe.setRole(request.role());
        employe.setAgence(agence);
    }
}
