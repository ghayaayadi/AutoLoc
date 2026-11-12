package tn.esprit.autoloc.dto.response;

import java.time.LocalDate;

/** Donnees renvoyees au client pour un client. */
public record ClientResponse(
        Long idClient,
        String nom,
        String prenom,
        String email,
        String telephone,
        String numPermis,
        LocalDate dateInscription) {
}
