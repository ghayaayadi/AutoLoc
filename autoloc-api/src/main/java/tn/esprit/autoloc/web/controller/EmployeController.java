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
import tn.esprit.autoloc.dto.request.EmployeRequest;
import tn.esprit.autoloc.dto.response.EmployeResponse;
import tn.esprit.autoloc.service.IEmployeService;

@RestController
@RequestMapping("/employes")
@RequiredArgsConstructor
@Tag(name = "Employes", description = "Gestion des employes")
public class EmployeController {

    private final IEmployeService employeService;

    @Operation(summary = "Creer un employe",
            description = "L'identifiant est genere par la base : il ne doit pas etre fourni.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Creation reussie"),
            @ApiResponse(responseCode = "400", description = "Donnees invalides"),
            @ApiResponse(responseCode = "409", description = "Valeur deja utilisee (contrainte d'unicite)")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeResponse create(@Valid @RequestBody EmployeRequest request) {
        return employeService.create(request);
    }

    @Operation(summary = "Consulter un employe par son identifiant")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ressource trouvee"),
            @ApiResponse(responseCode = "404", description = "Ressource introuvable")
    })
    @GetMapping("/{id}")
    public EmployeResponse findById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        return employeService.findById(id);
    }

    @Operation(summary = "Lister les employes")
    @ApiResponse(responseCode = "200", description = "Liste (eventuellement vide)")
    @GetMapping
    public List<EmployeResponse> findAll() {
        return employeService.findAll();
    }

    @Operation(summary = "Modifier un employe")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Modification reussie"),
            @ApiResponse(responseCode = "400", description = "Donnees invalides"),
            @ApiResponse(responseCode = "404", description = "Ressource introuvable"),
            @ApiResponse(responseCode = "409", description = "Valeur deja utilisee (contrainte d'unicite)")
    })
    @PutMapping("/{id}")
    public EmployeResponse update(@Parameter(description = "Identifiant") @PathVariable Long id,
                              @Valid @RequestBody EmployeRequest request) {
        return employeService.update(id, request);
    }

    @Operation(summary = "Supprimer un employe")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Suppression reussie"),
            @ApiResponse(responseCode = "404", description = "Ressource introuvable")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        employeService.deleteById(id);
    }
}
