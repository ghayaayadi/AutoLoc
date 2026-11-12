package tn.esprit.autoloc.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.dto.request.ContratRequest;
import tn.esprit.autoloc.dto.response.ContratResponse;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.ContratMapper;
import tn.esprit.autoloc.repository.IContratRepository;
import tn.esprit.autoloc.repository.IPaiementRepository;
import tn.esprit.autoloc.repository.IReservationRepository;
import tn.esprit.autoloc.service.IContratService;

@Service
@RequiredArgsConstructor
public class ContratServiceImpl implements IContratService {

    private final IContratRepository contratRepository;
    private final ContratMapper contratMapper;
    private final IPaiementRepository paiementRepository;
    private final IReservationRepository reservationRepository;

    @Override
    @Transactional
    public ContratResponse create(ContratRequest request) {
        Reservation reservation = chargerReservation(request.reservationId());
        Contrat entity = contratMapper.toEntity(request, reservation);
        return contratMapper.toResponse(contratRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public ContratResponse findById(Long id) {
        return contratMapper.toResponse(getOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContratResponse> findAll() {
        return contratRepository.findAll().stream().map(contratMapper::toResponse).toList();
    }

    @Override
    @Transactional
    public ContratResponse update(Long id, ContratRequest request) {
        Contrat existant = getOrThrow(id);
        Reservation reservation = chargerReservation(request.reservationId());
        contratMapper.updateEntity(existant, request, reservation);
        return contratMapper.toResponse(contratRepository.save(existant));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Contrat existant = getOrThrow(id);
        // pas de cascade dans l'entite : on supprime d'abord les paiements du contrat
        paiementRepository.deleteAll(existant.getPaiements());
        contratRepository.delete(existant);
    }

    private Contrat getOrThrow(Long id) {
        return contratRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contrat", id));
    }

    private Reservation chargerReservation(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", id));
    }
}
