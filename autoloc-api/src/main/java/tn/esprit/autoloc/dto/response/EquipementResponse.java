package tn.esprit.autoloc.dto.response;

/** Donnees renvoyees au client pour un equipement. */
public record EquipementResponse(
        Long idEquipement,
        String libelle) {
}
