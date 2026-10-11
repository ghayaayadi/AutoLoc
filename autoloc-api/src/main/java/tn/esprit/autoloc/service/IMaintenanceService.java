package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.domain.Maintenance;

public interface IMaintenanceService {

    Maintenance create(Maintenance entity);

    Maintenance getById(Long id);

    List<Maintenance> getAll();

    Maintenance update(Long id, Maintenance entity);

    void delete(Long id);
}
