package tn.esprit.autoloc.dto.response;

/** Donnees renvoyees au client pour une agence. */
public record AgenceResponse(
        Long idAgence,
        String nom,
        String ville,
        String adresse,
        String telephone) {
}
