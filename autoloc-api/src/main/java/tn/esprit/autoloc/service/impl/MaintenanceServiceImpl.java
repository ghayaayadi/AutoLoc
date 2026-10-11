package tn.esprit.autoloc.service.impl;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.repository.IMaintenanceRepository;
import tn.esprit.autoloc.service.IMaintenanceService;

@Service
@RequiredArgsConstructor
public class MaintenanceServiceImpl implements IMaintenanceService {

    private final IMaintenanceRepository repository;

    @Override
    @Transactional
    public Maintenance create(Maintenance entity) {
        return repository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Maintenance getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Maintenance introuvable : id=" + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Maintenance> getAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public Maintenance update(Long id, Maintenance entity) {
        Maintenance existing = getById(id);
        BeanUtils.copyProperties(entity, existing, "id");
        return repository.save(existing);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Maintenance introuvable : id=" + id);
        }
        repository.deleteById(id);
    }
}
