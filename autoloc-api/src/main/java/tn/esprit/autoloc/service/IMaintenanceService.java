package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.dto.request.MaintenanceRequest;
import tn.esprit.autoloc.dto.response.MaintenanceResponse;

public interface IMaintenanceService {

    MaintenanceResponse create(MaintenanceRequest request);

    MaintenanceResponse findById(Long id);

    List<MaintenanceResponse> findAll();

    MaintenanceResponse update(Long id, MaintenanceRequest request);

    void deleteById(Long id);
}
