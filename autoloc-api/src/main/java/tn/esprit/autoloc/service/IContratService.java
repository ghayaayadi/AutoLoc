package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.dto.request.ContratRequest;
import tn.esprit.autoloc.dto.response.ContratResponse;

public interface IContratService {

    ContratResponse create(ContratRequest request);

    ContratResponse findById(Long id);

    List<ContratResponse> findAll();

    ContratResponse update(Long id, ContratRequest request);

    void deleteById(Long id);
}
