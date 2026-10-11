package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.domain.Reservation;

public interface IReservationService {

    Reservation create(Reservation entity);

    Reservation getById(Long id);

    List<Reservation> getAll();

    Reservation update(Long id, Reservation entity);

    void delete(Long id);
}
