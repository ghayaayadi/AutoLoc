package tn.esprit.autoloc.service.impl;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.repository.IClientRepository;
import tn.esprit.autoloc.service.IClientService;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements IClientService {

    private final IClientRepository repository;

    @Override
    @Transactional
    public Client create(Client entity) {
        return repository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Client getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client introuvable : id=" + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Client> getAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public Client update(Long id, Client entity) {
        Client existing = getById(id);
        BeanUtils.copyProperties(entity, existing, "id");
        return repository.save(existing);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Client introuvable : id=" + id);
        }
        repository.deleteById(id);
    }
}
