package tn.esprit.autoloc.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.dto.request.EquipementRequest;
import tn.esprit.autoloc.dto.response.EquipementResponse;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.EquipementMapper;
import tn.esprit.autoloc.repository.IEquipementRepository;
import tn.esprit.autoloc.service.IEquipementService;

@Service
@RequiredArgsConstructor
public class EquipementServiceImpl implements IEquipementService {

    private final IEquipementRepository equipementRepository;
    private final EquipementMapper equipementMapper;

    @Override
    @Transactional
    public EquipementResponse create(EquipementRequest request) {
        Equipement entity = equipementMapper.toEntity(request);
        return equipementMapper.toResponse(equipementRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public EquipementResponse findById(Long id) {
        return equipementMapper.toResponse(getOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EquipementResponse> findAll() {
        return equipementRepository.findAll().stream().map(equipementMapper::toResponse).toList();
    }

    @Override
    @Transactional
    public EquipementResponse update(Long id, EquipementRequest request) {
        Equipement existant = getOrThrow(id);
        equipementMapper.updateEntity(existant, request);
        return equipementMapper.toResponse(equipementRepository.save(existant));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!equipementRepository.existsById(id)) {
            throw new ResourceNotFoundException("Equipement", id);
        }
        equipementRepository.deleteById(id);
    }

    private Equipement getOrThrow(Long id) {
        return equipementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipement", id));
    }
}
