package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.dto.request.EquipementRequest;
import tn.esprit.autoloc.dto.response.EquipementResponse;

public interface IEquipementService {

    EquipementResponse create(EquipementRequest request);

    EquipementResponse findById(Long id);

    List<EquipementResponse> findAll();

    EquipementResponse update(Long id, EquipementRequest request);

    void deleteById(Long id);
}
