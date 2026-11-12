package tn.esprit.autoloc.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.dto.request.VehiculeRequest;
import tn.esprit.autoloc.dto.response.VehiculeResponse;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.VehiculeMapper;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.repository.IEquipementRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;
import tn.esprit.autoloc.service.IVehiculeService;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {

    private final IVehiculeRepository vehiculeRepository;
    private final VehiculeMapper vehiculeMapper;
    private final IAgenceRepository agenceRepository;
    private final IEquipementRepository equipementRepository;

    @Override
    @Transactional
    public VehiculeResponse create(VehiculeRequest request) {
        Agence agence = chargerAgence(request.agenceId());
        Set<Equipement> equipements = chargerEquipements(request.equipementIds());
        Vehicule entity = vehiculeMapper.toEntity(request, agence, equipements);
        return vehiculeMapper.toResponse(vehiculeRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public VehiculeResponse findById(Long id) {
        return vehiculeMapper.toResponse(getOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculeResponse> findAll() {
        return vehiculeRepository.findAll().stream().map(vehiculeMapper::toResponse).toList();
    }

    @Override
    @Transactional
    public VehiculeResponse update(Long id, VehiculeRequest request) {
        Vehicule existant = getOrThrow(id);
        Agence agence = chargerAgence(request.agenceId());
        Set<Equipement> equipements = chargerEquipements(request.equipementIds());
        vehiculeMapper.updateEntity(existant, request, agence, equipements);
        return vehiculeMapper.toResponse(vehiculeRepository.save(existant));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!vehiculeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicule", id);
        }
        vehiculeRepository.deleteById(id);
    }

    private Vehicule getOrThrow(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", id));
    }

    /** Relation facultative : si agenceId est absent, la valeur reste null. */
    private Agence chargerAgence(Long id) {
        if (id == null) {
            return null;
        }
        return agenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", id));
    }

    private Set<Equipement> chargerEquipements(Set<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return new HashSet<>();
        }
        Set<Equipement> trouves = new HashSet<>(equipementRepository.findAllById(ids));
        for (Long id : ids) {
            boolean present = trouves.stream().anyMatch(eq -> eq.getIdEquipement().equals(id));
            if (!present) {
                throw new ResourceNotFoundException("Equipement", id);
            }
        }
        return trouves;
    }
}
