package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.domain.Contrat;

public interface IContratService {

    Contrat create(Contrat entity);

    Contrat getById(Long id);

    List<Contrat> getAll();

    Contrat update(Long id, Contrat entity);

    void delete(Long id);
}
