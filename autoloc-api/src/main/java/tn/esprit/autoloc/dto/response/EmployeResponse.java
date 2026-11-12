package tn.esprit.autoloc.dto.response;

import tn.esprit.autoloc.domain.RoleEmploye;

/** Donnees renvoyees au client pour un employe. */
public record EmployeResponse(
        Long idEmploye,
        String nom,
        String prenom,
        RoleEmploye role,
        Long agenceId,
        String agenceNom) {
}
