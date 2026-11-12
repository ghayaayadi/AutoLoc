package tn.esprit.autoloc.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.dto.request.ReservationRequest;
import tn.esprit.autoloc.dto.response.ReservationResponse;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.ReservationMapper;
import tn.esprit.autoloc.repository.IClientRepository;
import tn.esprit.autoloc.repository.IReservationRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;
import tn.esprit.autoloc.service.IReservationService;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private final IReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final IVehiculeRepository vehiculeRepository;
    private final IClientRepository clientRepository;

    @Override
    @Transactional
    public ReservationResponse create(ReservationRequest request) {
        valider(request);
        Vehicule vehicule = chargerVehicule(request.vehiculeId());
        Client client = chargerClient(request.clientId());
        Reservation entity = reservationMapper.toEntity(request, vehicule, client);
        return reservationMapper.toResponse(reservationRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public ReservationResponse findById(Long id) {
        return reservationMapper.toResponse(getOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationResponse> findAll() {
        return reservationRepository.findAll().stream().map(reservationMapper::toResponse).toList();
    }

    @Override
    @Transactional
    public ReservationResponse update(Long id, ReservationRequest request) {
        Reservation existant = getOrThrow(id);
        valider(request);
        Vehicule vehicule = chargerVehicule(request.vehiculeId());
        Client client = chargerClient(request.clientId());
        reservationMapper.updateEntity(existant, request, vehicule, client);
        return reservationMapper.toResponse(reservationRepository.save(existant));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reservation", id);
        }
        reservationRepository.deleteById(id);
    }

    private Reservation getOrThrow(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", id));
    }

    private Vehicule chargerVehicule(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", id));
    }

    private Client chargerClient(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client", id));
    }

    private void valider(ReservationRequest request) {
        if (request.dateFin().isBefore(request.dateDebut())) {
            throw new IllegalArgumentException("La date de fin est anterieure a la date de debut");
        }
    }
}
