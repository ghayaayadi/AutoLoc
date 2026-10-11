package tn.esprit.autoloc.service.impl;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.repository.IEmployeRepository;
import tn.esprit.autoloc.service.IEmployeService;

@Service
@RequiredArgsConstructor
public class EmployeServiceImpl implements IEmployeService {

    private final IEmployeRepository repository;

    @Override
    @Transactional
    public Employe create(Employe entity) {
        return repository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Employe getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employe introuvable : id=" + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employe> getAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public Employe update(Long id, Employe entity) {
        Employe existing = getById(id);
        BeanUtils.copyProperties(entity, existing, "id");
        return repository.save(existing);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Employe introuvable : id=" + id);
        }
        repository.deleteById(id);
    }
}
