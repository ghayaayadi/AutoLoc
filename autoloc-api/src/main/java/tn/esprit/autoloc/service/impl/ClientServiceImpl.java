package tn.esprit.autoloc.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.dto.request.ClientRequest;
import tn.esprit.autoloc.dto.response.ClientResponse;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.ClientMapper;
import tn.esprit.autoloc.repository.IClientRepository;
import tn.esprit.autoloc.service.IClientService;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements IClientService {

    private final IClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    @Transactional
    public ClientResponse create(ClientRequest request) {
        Client entity = clientMapper.toEntity(request);
        return clientMapper.toResponse(clientRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public ClientResponse findById(Long id) {
        return clientMapper.toResponse(getOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientResponse> findAll() {
        return clientRepository.findAll().stream().map(clientMapper::toResponse).toList();
    }

    @Override
    @Transactional
    public ClientResponse update(Long id, ClientRequest request) {
        Client existant = getOrThrow(id);
        clientMapper.updateEntity(existant, request);
        return clientMapper.toResponse(clientRepository.save(existant));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Client", id);
        }
        clientRepository.deleteById(id);
    }

    private Client getOrThrow(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client", id));
    }
}
