package tn.esprit.autoloc.service.impl;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.repository.IContratRepository;
import tn.esprit.autoloc.service.IContratService;

@Service
@RequiredArgsConstructor
public class ContratServiceImpl implements IContratService {

    private final IContratRepository repository;

    @Override
    @Transactional
    public Contrat create(Contrat entity) {
        return repository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Contrat getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Contrat introuvable : id=" + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Contrat> getAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public Contrat update(Long id, Contrat entity) {
        Contrat existing = getById(id);
        BeanUtils.copyProperties(entity, existing, "id");
        return repository.save(existing);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Contrat introuvable : id=" + id);
        }
        repository.deleteById(id);
    }
}
