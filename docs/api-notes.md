# Atelier 5 — Notes : API REST, Postman et Swagger

## A. Endpoints (Agences, Clients, Vehicules)

Pour chaque ressource (`/api/agences`, `/api/clients`, `/api/vehicules`) :

| Verbe et route | Service appele | Succes | Erreurs |
|---|---|---|---|
| GET /api/{ressource} | findAll() | 200 | — |
| GET /api/{ressource}/{id} | findById(id) | 200 | 404 |
| POST /api/{ressource} | create(entite) | 201 | 400 |
| PUT /api/{ressource}/{id} | update(id, entite) | 200 | 400, 404 |
| DELETE /api/{ressource}/{id} | deleteById(id) | 204 | 404 |

## B. Annotations Jackson

| Association | Annotation | Justification |
|---|---|---|
| Agence.employes, Agence.vehicules | @JsonIgnore | Cote inverse (mappedBy) : evite la boucle Agence → Employe/Vehicule → Agence |
| Client.reservations | @JsonIgnore | Idem |
| Vehicule.maintenances, Vehicule.reservations | @JsonIgnore | Idem |
| Equipement.vehicules | @JsonIgnore | Idem (ManyToMany inverse) |
| Reservation.contrat | @JsonIgnore | Idem (OneToOne inverse) |
| Contrat.paiements | @JsonIgnore | Idem (pas de cascade dans le modele) |

`@JsonIgnore` supprime la propriete du JSON (lecture et ecriture) ; `@JsonBackReference` fait la meme chose cote parent mais fonctionne en paire avec `@JsonManagedReference` pour qu'une composition (enfant cree dans le JSON du parent) soit sérialisee d'un cote et reconstruite a la lecture.

## C. Postman et Swagger

- **Postman** sert a tester : requetes rejouables et assertions (ex. « Consulter une agence inexistante » doit renvoyer 404).
- **Swagger UI** (`/swagger-ui.html`) documente et permet d'explorer l'API : les `@Operation` et `@ApiResponse` y apparaissent.

## D. Anomalies SonarQube for IDE

> A completer apres votre analyse de `web.controller` (au moins deux).

| Regle | Explication | Correction |
|---|---|---|
| a completer | | |
| a completer | | |

## E. Questions

> A rediger avec vos mots (voir les 5 questions de l'Atelier 5).
