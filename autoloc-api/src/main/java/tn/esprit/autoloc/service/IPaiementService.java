package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.domain.Paiement;

public interface IPaiementService {

    Paiement create(Paiement entity);

    Paiement getById(Long id);

    List<Paiement> getAll();

    Paiement update(Long id, Paiement entity);

    void delete(Long id);
}
