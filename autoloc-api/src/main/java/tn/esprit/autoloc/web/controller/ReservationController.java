package tn.esprit.autoloc.web.controller;

import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.autoloc.dto.request.ReservationRequest;
import tn.esprit.autoloc.dto.response.ReservationResponse;
import tn.esprit.autoloc.service.IReservationService;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
@Tag(name = "Reservations", description = "Gestion des reservations")
public class ReservationController {

    private final IReservationService reservationService;

    @Operation(summary = "Creer une reservation",
            description = "L'identifiant est genere par la base : il ne doit pas etre fourni.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Creation reussie"),
            @ApiResponse(responseCode = "400", description = "Donnees invalides"),
            @ApiResponse(responseCode = "409", description = "Valeur deja utilisee (contrainte d'unicite)")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationResponse create(@Valid @RequestBody ReservationRequest request) {
        return reservationService.create(request);
    }

    @Operation(summary = "Consulter une reservation par son identifiant")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ressource trouvee"),
            @ApiResponse(responseCode = "404", description = "Ressource introuvable")
    })
    @GetMapping("/{id}")
    public ReservationResponse findById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        return reservationService.findById(id);
    }

    @Operation(summary = "Lister les reservations")
    @ApiResponse(responseCode = "200", description = "Liste (eventuellement vide)")
    @GetMapping
    public List<ReservationResponse> findAll() {
        return reservationService.findAll();
    }

    @Operation(summary = "Modifier une reservation")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Modification reussie"),
            @ApiResponse(responseCode = "400", description = "Donnees invalides"),
            @ApiResponse(responseCode = "404", description = "Ressource introuvable"),
            @ApiResponse(responseCode = "409", description = "Valeur deja utilisee (contrainte d'unicite)")
    })
    @PutMapping("/{id}")
    public ReservationResponse update(@Parameter(description = "Identifiant") @PathVariable Long id,
                              @Valid @RequestBody ReservationRequest request) {
        return reservationService.update(id, request);
    }

    @Operation(summary = "Supprimer une reservation")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Suppression reussie"),
            @ApiResponse(responseCode = "404", description = "Ressource introuvable")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        reservationService.deleteById(id);
    }
}
