package tn.esprit.autoloc.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.dto.request.MaintenanceRequest;
import tn.esprit.autoloc.dto.response.MaintenanceResponse;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.MaintenanceMapper;
import tn.esprit.autoloc.repository.IMaintenanceRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;
import tn.esprit.autoloc.service.IMaintenanceService;

@Service
@RequiredArgsConstructor
public class MaintenanceServiceImpl implements IMaintenanceService {

    private final IMaintenanceRepository maintenanceRepository;
    private final MaintenanceMapper maintenanceMapper;
    private final IVehiculeRepository vehiculeRepository;

    @Override
    @Transactional
    public MaintenanceResponse create(MaintenanceRequest request) {
        valider(request);
        Vehicule vehicule = chargerVehicule(request.vehiculeId());
        Maintenance entity = maintenanceMapper.toEntity(request, vehicule);
        return maintenanceMapper.toResponse(maintenanceRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public MaintenanceResponse findById(Long id) {
        return maintenanceMapper.toResponse(getOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaintenanceResponse> findAll() {
        return maintenanceRepository.findAll().stream().map(maintenanceMapper::toResponse).toList();
    }

    @Override
    @Transactional
    public MaintenanceResponse update(Long id, MaintenanceRequest request) {
        Maintenance existant = getOrThrow(id);
        valider(request);
        Vehicule vehicule = chargerVehicule(request.vehiculeId());
        maintenanceMapper.updateEntity(existant, request, vehicule);
        return maintenanceMapper.toResponse(maintenanceRepository.save(existant));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!maintenanceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Maintenance", id);
        }
        maintenanceRepository.deleteById(id);
    }

    private Maintenance getOrThrow(Long id) {
        return maintenanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance", id));
    }

    private Vehicule chargerVehicule(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", id));
    }

    private void valider(MaintenanceRequest request) {
        if (request.dateFin().isBefore(request.dateDebut())) {
            throw new IllegalArgumentException("La date de fin est anterieure a la date de debut");
        }
    }
}
