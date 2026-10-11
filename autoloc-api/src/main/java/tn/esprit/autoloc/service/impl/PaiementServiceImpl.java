package tn.esprit.autoloc.service.impl;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.repository.IPaiementRepository;
import tn.esprit.autoloc.service.IPaiementService;

@Service
@RequiredArgsConstructor
public class PaiementServiceImpl implements IPaiementService {

    private final IPaiementRepository repository;

    @Override
    @Transactional
    public Paiement create(Paiement entity) {
        return repository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Paiement getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Paiement introuvable : id=" + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Paiement> getAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public Paiement update(Long id, Paiement entity) {
        Paiement existing = getById(id);
        BeanUtils.copyProperties(entity, existing, "id");
        return repository.save(existing);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Paiement introuvable : id=" + id);
        }
        repository.deleteById(id);
    }
}
