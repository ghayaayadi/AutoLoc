# Atelier 6 — Notes : DTO, validation et gestion centralisee des erreurs

Projet : AutoLoc (`autoloc-api`) — Auteur : Ghaya Ayadi

## A. Architecture

Controller (`@Valid @RequestBody XRequest`) -> Service (DTO in / DTO out) -> Mapper (DTO <-> entite) -> Repository.
Aucune entite JPA n'est plus exposee par l'API : les `@JsonIgnore` de l'Atelier 5 restent dans les entites mais ne servent plus.

| Package | Contenu |
|---|---|
| `dto.request` | `XRequest` (records) : donnees recues, **sans id**, avec contraintes Bean Validation |
| `dto.response` | `XResponse` (records) : id + champs + ids des relations (+ un libelle utile) |
| `mapper` | `XMapper` (`@Component`) : `toEntity`, `updateEntity`, `toResponse` |
| `service` / `service.impl` | prennent un `XRequest`, renvoient un `XResponse` ; chargent les relations par leur id |
| `web.exception` | `GlobalExceptionHandler` (`@RestControllerAdvice`) + `ApiError` |

Relations : le client envoie `agenceId`, `vehiculeId`, `clientId`, `reservationId`, `equipementIds` (et non un objet imbrique) ;
le service charge l'entite (404 si l'id n'existe pas) puis la passe au mapper.

## B. Contraintes de validation (resume)

| Annotation | Utilisee pour |
|---|---|
| `@NotBlank` | textes obligatoires (nom, prenom, email, ville, immatriculation...) |
| `@NotNull` | enums, dates, nombres, ids obligatoires |
| `@Size(max=...)` | longueur alignee sur `@Column(length=...)` de l'entite |
| `@Email` | `Client.email` |
| `@Pattern` | telephone (8 a 20 chiffres, `+` autorise) |
| `@PastOrPresent` | `Client.dateInscription` |
| `@DecimalMin("0.0")` + `@Digits` | tarif journalier, montant total |
| Regle metier (service) | `dateFin >= dateDebut` (Reservation, Maintenance) -> `IllegalArgumentException` -> 400 |

## C. Codes HTTP renvoyes par le `GlobalExceptionHandler`

| Exception | Code | Cas |
|---|---|---|
| `MethodArgumentNotValidException` | 400 | echec `@Valid` : `fieldErrors` liste chaque champ |
| `HttpMessageNotReadableException` | 400 | JSON mal forme, enum inconnue, date invalide |
| `MethodArgumentTypeMismatchException` | 400 | `/clients/abc` |
| `IllegalArgumentException` | 400 | regle metier violee |
| `ResourceNotFoundException` | 404 | id inexistant (ressource ou relation) |
| `NoResourceFoundException` | 404 | route inexistante |
| `HttpRequestMethodNotSupportedException` | 405 | mauvais verbe HTTP |
| `DataIntegrityViolationException` | 409 | email / numPermis / immatriculation deja utilises |
| `Exception` | 500 | filet de securite (detail journalise, jamais renvoye) |

Format unique :
```json
{ "timestamp": "...", "status": 400, "error": "Bad Request",
  "message": "Erreur de validation des donnees", "path": "/api/clients",
  "fieldErrors": { "nom": "Le nom est obligatoire", "email": "Le format de l'email est invalide" } }
```

## D. Questions

> A rediger avec vos mots : (1) pourquoi un DTO plutot que l'entite ? (2) difference `@NotNull` / `@NotBlank` / `@NotEmpty` ?
> (3) que fait `@Valid` et que se passe-t-il s'il est oublie ? (4) pourquoi `@RestControllerAdvice` ?
