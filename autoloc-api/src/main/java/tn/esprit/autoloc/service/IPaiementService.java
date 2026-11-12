package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.dto.response.PaiementResponse;

/** Lecture seule : les paiements sont geres a travers le Contrat. */
public interface IPaiementService {

    PaiementResponse findById(Long id);

    List<PaiementResponse> findAll();
}
