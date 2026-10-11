package tn.esprit.autoloc.service.impl;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.repository.IEquipementRepository;
import tn.esprit.autoloc.service.IEquipementService;

@Service
@RequiredArgsConstructor
public class EquipementServiceImpl implements IEquipementService {

    private final IEquipementRepository repository;

    @Override
    @Transactional
    public Equipement create(Equipement entity) {
        return repository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Equipement getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Equipement introuvable : id=" + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Equipement> getAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public Equipement update(Long id, Equipement entity) {
        Equipement existing = getById(id);
        BeanUtils.copyProperties(entity, existing, "id");
        return repository.save(existing);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Equipement introuvable : id=" + id);
        }
        repository.deleteById(id);
    }
}
