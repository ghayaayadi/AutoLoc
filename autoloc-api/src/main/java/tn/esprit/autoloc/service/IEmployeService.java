package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.domain.Employe;

public interface IEmployeService {

    Employe create(Employe entity);

    Employe getById(Long id);

    List<Employe> getAll();

    Employe update(Long id, Employe entity);

    void delete(Long id);
}
