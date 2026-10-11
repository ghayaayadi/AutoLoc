package tn.esprit.autoloc.service.impl;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.service.IAgenceService;

@Service
@RequiredArgsConstructor
public class AgenceServiceImpl implements IAgenceService {

    private final IAgenceRepository repository;

    @Override
    @Transactional
    public Agence create(Agence entity) {
        return repository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Agence getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agence introuvable : id=" + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Agence> getAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public Agence update(Long id, Agence entity) {
        Agence existing = getById(id);
        BeanUtils.copyProperties(entity, existing, "id");
        return repository.save(existing);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Agence introuvable : id=" + id);
        }
        repository.deleteById(id);
    }
}
