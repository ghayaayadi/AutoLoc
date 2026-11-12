package tn.esprit.autoloc.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.dto.request.EmployeRequest;
import tn.esprit.autoloc.dto.response.EmployeResponse;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.EmployeMapper;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.repository.IEmployeRepository;
import tn.esprit.autoloc.service.IEmployeService;

@Service
@RequiredArgsConstructor
public class EmployeServiceImpl implements IEmployeService {

    private final IEmployeRepository employeRepository;
    private final EmployeMapper employeMapper;
    private final IAgenceRepository agenceRepository;

    @Override
    @Transactional
    public EmployeResponse create(EmployeRequest request) {
        Agence agence = chargerAgence(request.agenceId());
        Employe entity = employeMapper.toEntity(request, agence);
        return employeMapper.toResponse(employeRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeResponse findById(Long id) {
        return employeMapper.toResponse(getOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeResponse> findAll() {
        return employeRepository.findAll().stream().map(employeMapper::toResponse).toList();
    }

    @Override
    @Transactional
    public EmployeResponse update(Long id, EmployeRequest request) {
        Employe existant = getOrThrow(id);
        Agence agence = chargerAgence(request.agenceId());
        employeMapper.updateEntity(existant, request, agence);
        return employeMapper.toResponse(employeRepository.save(existant));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!employeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employe", id);
        }
        employeRepository.deleteById(id);
    }

    private Employe getOrThrow(Long id) {
        return employeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employe", id));
    }

    /** Relation facultative : si agenceId est absent, la valeur reste null. */
    private Agence chargerAgence(Long id) {
        if (id == null) {
            return null;
        }
        return agenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", id));
    }
}
