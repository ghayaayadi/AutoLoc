package tn.esprit.autoloc.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.dto.request.ReservationRequest;
import tn.esprit.autoloc.dto.response.ReservationResponse;

/** Conversion entre l'entite Reservation et ses DTO (request / response). */
@Component
public class ReservationMapper {

    /** Request -> nouvelle entite. Les relations sont deja chargees par le service. */
    public Reservation toEntity(ReservationRequest request, Vehicule vehicule, Client client) {
        Reservation reservation = new Reservation();
        apply(reservation, request, vehicule, client);
        return reservation;
    }

    /** Recopie les champs modifiables sur une entite existante (l'identifiant n'est jamais touche). */
    public void updateEntity(Reservation reservation, ReservationRequest request, Vehicule vehicule, Client client) {
        apply(reservation, request, vehicule, client);
    }

    /** Entite -> response (aucune entite n'est exposee au client). */
    public ReservationResponse toResponse(Reservation reservation) {
        return new ReservationResponse(
                reservation.getIdReservation(),
                reservation.getDateDebut(),
                reservation.getDateFin(),
                reservation.getStatut(),
                reservation.getVehicule() == null ? null : reservation.getVehicule().getIdVehicule(),
                reservation.getVehicule() == null ? null : reservation.getVehicule().getImmatriculation(),
                reservation.getClient() == null ? null : reservation.getClient().getIdClient(),
                reservation.getClient() == null ? null : reservation.getClient().getNom() + " " + reservation.getClient().getPrenom());
    }

    private void apply(Reservation reservation, ReservationRequest request, Vehicule vehicule, Client client) {
        reservation.setDateDebut(request.dateDebut());
        reservation.setDateFin(request.dateFin());
        reservation.setStatut(request.statut());
        reservation.setVehicule(vehicule);
        reservation.setClient(client);
    }
}
