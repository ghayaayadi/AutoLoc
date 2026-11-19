package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.autoloc.dto.request.ReservationWithContratRequestDTO;
import tn.esprit.autoloc.dto.response.ContratDTO;
import tn.esprit.autoloc.dto.response.ReservationResponseDTO;
import tn.esprit.autoloc.dto.response.VehiculeDTO;
import tn.esprit.autoloc.service.IAffectationService;

/** Expose uniquement les services d'affectation : aucune logique metier dans ce controleur. */
@RestController
@RequestMapping("/affectations")
@RequiredArgsConstructor
@Tag(name = "Affectations", description = "Affectations entre reservations, clients, vehicules, equipements, maintenances et contrats")
public class AffectationController {

    private final IAffectationService affectationService;

    @Operation(summary = "Affecter une reservation a un client (retrouve par nom et prenom)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Affectation reussie"),
            @ApiResponse(responseCode = "400", description = "Parametre invalide, client ambigu ou reservation annulee/terminee"),
            @ApiResponse(responseCode = "404", description = "Reservation ou client introuvable")
    })
    @PutMapping("/reservations/{idReservation}/client")
    public ReservationResponseDTO affecterReservationAClient(
            @Parameter(description = "Identifiant de la reservation") @PathVariable Long idReservation,
            @RequestParam String nom,
            @RequestParam String prenom) {
        return affectationService.affecterReservationAClient(idReservation, nom, prenom);
    }

    @Operation(summary = "Affecter un equipement a un vehicule")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Affectation reussie (idempotente)"),
            @ApiResponse(responseCode = "404", description = "Vehicule ou equipement introuvable")
    })
    @PutMapping("/vehicules/{idVehicule}/equipements/{idEquipement}")
    public VehiculeDTO affecterVehiculeAEquipement(
            @Parameter(description = "Identifiant du vehicule") @PathVariable Long idVehicule,
            @Parameter(description = "Identifiant de l'equipement") @PathVariable Long idEquipement) {
        return affectationService.affecterVehiculeAEquipement(idVehicule, idEquipement);
    }

    @Operation(summary = "Affecter une maintenance a un vehicule (retrouve par immatriculation)")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Maintenance creee et affectee"),
            @ApiResponse(responseCode = "400", description = "Parametre invalide ou vehicule actuellement loue"),
            @ApiResponse(responseCode = "404", description = "Vehicule introuvable")
    })
    @PostMapping("/maintenances")
    @ResponseStatus(HttpStatus.CREATED)
    public VehiculeDTO affecterMaintenanceAvehicule(@RequestParam String description,
                                                    @RequestParam String immatriculation) {
        return affectationService.affecterMaintenanceAvehicule(description, immatriculation);
    }

    @Operation(summary = "Affecter un contrat a la reservation d'une periode donnee")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Contrat cree et affecte"),
            @ApiResponse(responseCode = "400", description = "Dates invalides ou plusieurs reservations correspondantes"),
            @ApiResponse(responseCode = "404", description = "Aucune reservation sans contrat pour cette periode")
    })
    @PostMapping("/contrats")
    @ResponseStatus(HttpStatus.CREATED)
    public ContratDTO affecterContratAReservation(
            @Parameter(description = "Format yyyy-MM-dd") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateSignature,
            @Parameter(description = "Format yyyy-MM-dd") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @Parameter(description = "Format yyyy-MM-dd") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin) {
        return affectationService.affecterContratAReservation(dateSignature, dateDebut, dateFin);
    }

    @Operation(summary = "Creer une reservation et son contrat en une seule operation")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Reservation et contrat crees"),
            @ApiResponse(responseCode = "400", description = "Donnees invalides ou vehicule en maintenance"),
            @ApiResponse(responseCode = "404", description = "Vehicule ou client introuvable")
    })
    @PostMapping("/reservations-avec-contrat")
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationResponseDTO creerReservationEtContratAssocie(
            @Valid @RequestBody ReservationWithContratRequestDTO requestDTO) {
        return affectationService.creerReservationEtContratAssocie(requestDTO);
    }
}
