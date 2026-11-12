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
import tn.esprit.autoloc.dto.request.AgenceRequest;
import tn.esprit.autoloc.dto.response.AgenceResponse;
import tn.esprit.autoloc.service.IAgenceService;

@RestController
@RequestMapping("/agences")
@RequiredArgsConstructor
@Tag(name = "Agences", description = "Gestion des agences")
public class AgenceController {

    private final IAgenceService agenceService;

    @Operation(summary = "Creer une agence",
            description = "L'identifiant est genere par la base : il ne doit pas etre fourni.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Creation reussie"),
            @ApiResponse(responseCode = "400", description = "Donnees invalides"),
            @ApiResponse(responseCode = "409", description = "Valeur deja utilisee (contrainte d'unicite)")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AgenceResponse create(@Valid @RequestBody AgenceRequest request) {
        return agenceService.create(request);
    }

    @Operation(summary = "Consulter une agence par son identifiant")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ressource trouvee"),
            @ApiResponse(responseCode = "404", description = "Ressource introuvable")
    })
    @GetMapping("/{id}")
    public AgenceResponse findById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        return agenceService.findById(id);
    }

    @Operation(summary = "Lister les agences")
    @ApiResponse(responseCode = "200", description = "Liste (eventuellement vide)")
    @GetMapping
    public List<AgenceResponse> findAll() {
        return agenceService.findAll();
    }

    @Operation(summary = "Modifier une agence")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Modification reussie"),
            @ApiResponse(responseCode = "400", description = "Donnees invalides"),
            @ApiResponse(responseCode = "404", description = "Ressource introuvable"),
            @ApiResponse(responseCode = "409", description = "Valeur deja utilisee (contrainte d'unicite)")
    })
    @PutMapping("/{id}")
    public AgenceResponse update(@Parameter(description = "Identifiant") @PathVariable Long id,
                              @Valid @RequestBody AgenceRequest request) {
        return agenceService.update(id, request);
    }

    @Operation(summary = "Supprimer une agence")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Suppression reussie"),
            @ApiResponse(responseCode = "404", description = "Ressource introuvable")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        agenceService.deleteById(id);
    }
}
