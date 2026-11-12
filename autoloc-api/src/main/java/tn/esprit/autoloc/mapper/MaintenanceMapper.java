package tn.esprit.autoloc.mapper;

import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.dto.request.MaintenanceRequest;
import tn.esprit.autoloc.dto.response.MaintenanceResponse;

/** Conversion entre l'entite Maintenance et ses DTO (request / response). */
@Component
public class MaintenanceMapper {

    /** Request -> nouvelle entite. Les relations sont deja chargees par le service. */
    public Maintenance toEntity(MaintenanceRequest request, Vehicule vehicule) {
        Maintenance maintenance = new Maintenance();
        apply(maintenance, request, vehicule);
        return maintenance;
    }

    /** Recopie les champs modifiables sur une entite existante (l'identifiant n'est jamais touche). */
    public void updateEntity(Maintenance maintenance, MaintenanceRequest request, Vehicule vehicule) {
        apply(maintenance, request, vehicule);
    }

    /** Entite -> response (aucune entite n'est exposee au client). */
    public MaintenanceResponse toResponse(Maintenance maintenance) {
        return new MaintenanceResponse(
                maintenance.getIdMaintenance(),
                maintenance.getDateDebut(),
                maintenance.getDateFin(),
                maintenance.getDescription(),
                maintenance.getVehicule() == null ? null : maintenance.getVehicule().getIdVehicule(),
                maintenance.getVehicule() == null ? null : maintenance.getVehicule().getImmatriculation());
    }

    private void apply(Maintenance maintenance, MaintenanceRequest request, Vehicule vehicule) {
        maintenance.setDateDebut(request.dateDebut());
        maintenance.setDateFin(request.dateFin());
        maintenance.setDescription(request.description());
        maintenance.setVehicule(vehicule);
    }
}
