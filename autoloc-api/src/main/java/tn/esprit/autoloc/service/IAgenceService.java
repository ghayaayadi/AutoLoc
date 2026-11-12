package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.dto.request.AgenceRequest;
import tn.esprit.autoloc.dto.response.AgenceResponse;

public interface IAgenceService {

    AgenceResponse create(AgenceRequest request);

    AgenceResponse findById(Long id);

    List<AgenceResponse> findAll();

    AgenceResponse update(Long id, AgenceRequest request);

    void deleteById(Long id);
}
