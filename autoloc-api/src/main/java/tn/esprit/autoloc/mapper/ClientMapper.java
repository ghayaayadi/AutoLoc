package tn.esprit.autoloc.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.dto.request.ClientRequest;
import tn.esprit.autoloc.dto.response.ClientResponse;

/** Conversion entre l'entite Client et ses DTO (request / response). */
@Component
public class ClientMapper {

    /** Request -> nouvelle entite. Les relations sont deja chargees par le service. */
    public Client toEntity(ClientRequest request) {
        Client client = new Client();
        apply(client, request);
        return client;
    }

    /** Recopie les champs modifiables sur une entite existante (l'identifiant n'est jamais touche). */
    public void updateEntity(Client client, ClientRequest request) {
        apply(client, request);
    }

    /** Entite -> response (aucune entite n'est exposee au client). */
    public ClientResponse toResponse(Client client) {
        return new ClientResponse(
                client.getIdClient(),
                client.getNom(),
                client.getPrenom(),
                client.getEmail(),
                client.getTelephone(),
                client.getNumPermis(),
                client.getDateInscription());
    }

    private void apply(Client client, ClientRequest request) {
        client.setNom(request.nom());
        client.setPrenom(request.prenom());
        client.setEmail(request.email());
        client.setTelephone(request.telephone());
        client.setNumPermis(request.numPermis());
        client.setDateInscription(request.dateInscription());
    }
}
