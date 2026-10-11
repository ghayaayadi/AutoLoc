package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.domain.Agence;

public interface IAgenceService {

    Agence create(Agence entity);

    Agence getById(Long id);

    List<Agence> getAll();

    Agence update(Long id, Agence entity);

    void delete(Long id);
}
