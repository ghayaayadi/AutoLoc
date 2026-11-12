package tn.esprit.autoloc.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.dto.request.EquipementRequest;
import tn.esprit.autoloc.dto.response.EquipementResponse;

/** Conversion entre l'entite Equipement et ses DTO (request / response). */
@Component
public class EquipementMapper {

    /** Request -> nouvelle entite. Les relations sont deja chargees par le service. */
    public Equipement toEntity(EquipementRequest request) {
        Equipement equipement = new Equipement();
        apply(equipement, request);
        return equipement;
    }

    /** Recopie les champs modifiables sur une entite existante (l'identifiant n'est jamais touche). */
    public void updateEntity(Equipement equipement, EquipementRequest request) {
        apply(equipement, request);
    }

    /** Entite -> response (aucune entite n'est exposee au client). */
    public EquipementResponse toResponse(Equipement equipement) {
        return new EquipementResponse(
                equipement.getIdEquipement(),
                equipement.getLibelle());
    }

    private void apply(Equipement equipement, EquipementRequest request) {
        equipement.setLibelle(request.libelle());
    }
}
