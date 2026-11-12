package tn.esprit.autoloc.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.dto.response.PaiementResponse;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.PaiementMapper;
import tn.esprit.autoloc.repository.IPaiementRepository;
import tn.esprit.autoloc.service.IPaiementService;

@Service
@RequiredArgsConstructor
public class PaiementServiceImpl implements IPaiementService {

    private final IPaiementRepository paiementRepository;
    private final PaiementMapper paiementMapper;

    @Override
    @Transactional(readOnly = true)
    public PaiementResponse findById(Long id) {
        return paiementRepository.findById(id)
                .map(paiementMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Paiement", id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaiementResponse> findAll() {
        return paiementRepository.findAll().stream().map(paiementMapper::toResponse).toList();
    }
}
