package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.dto.request.ReservationRequest;
import tn.esprit.autoloc.dto.response.ReservationResponse;

public interface IReservationService {

    ReservationResponse create(ReservationRequest request);

    ReservationResponse findById(Long id);

    List<ReservationResponse> findAll();

    ReservationResponse update(Long id, ReservationRequest request);

    void deleteById(Long id);
}
