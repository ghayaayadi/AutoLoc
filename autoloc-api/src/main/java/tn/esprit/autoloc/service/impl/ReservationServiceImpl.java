package tn.esprit.autoloc.service.impl;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.repository.IReservationRepository;
import tn.esprit.autoloc.service.IReservationService;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private final IReservationRepository repository;

    @Override
    @Transactional
    public Reservation create(Reservation entity) {
        return repository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Reservation getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reservation introuvable : id=" + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reservation> getAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public Reservation update(Long id, Reservation entity) {
        Reservation existing = getById(id);
        BeanUtils.copyProperties(entity, existing, "id");
        return repository.save(existing);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Reservation introuvable : id=" + id);
        }
        repository.deleteById(id);
    }
}
