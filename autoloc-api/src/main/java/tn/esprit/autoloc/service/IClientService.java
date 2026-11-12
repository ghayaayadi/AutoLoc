package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.dto.request.ClientRequest;
import tn.esprit.autoloc.dto.response.ClientResponse;

public interface IClientService {

    ClientResponse create(ClientRequest request);

    ClientResponse findById(Long id);

    List<ClientResponse> findAll();

    ClientResponse update(Long id, ClientRequest request);

    void deleteById(Long id);
}
