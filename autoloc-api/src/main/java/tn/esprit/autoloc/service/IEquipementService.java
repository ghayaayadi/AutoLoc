package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.domain.Equipement;

public interface IEquipementService {

    Equipement create(Equipement entity);

    Equipement getById(Long id);

    List<Equipement> getAll();

    Equipement update(Long id, Equipement entity);

    void delete(Long id);
}
