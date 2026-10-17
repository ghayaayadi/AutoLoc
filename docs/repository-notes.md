# Atelier 3 — Note : couche Repository

Projet : AutoLoc (`autoloc-api`) — Auteur : Ghaya Ayadi

## Choix d'interface

| Interface | Étend | Justification |
|---|---|---|
| IContratRepository | JpaRepository<Contrat, Long> | CRUD complet, `findAll` renvoie une `List`, `saveAndFlush` disponible. |
| IAgenceRepository, IEmployeRepository, IVehiculeRepository, IEquipementRepository, IClientRepository, IReservationRepository, IPaiementRepository, IMaintenanceRepository | JpaRepository<Entité, Long> | Même choix pour la cohérence : CRUD, listes, tri, pagination, flush. Aucune annotation `@Repository` nécessaire (proxy généré par Spring Data). |

## Anomalies SonarQube for IDE

| Anomalie | Règle / explication | Correction apportée |
|---|---|---|
| Imports inutilisés dans les entités (`ArrayList`, `List`, `HashSet`, `Set`) | S1128 : code mort | Imports supprimés |
| Injection par attribut / exception générique | Voir `service-notes.md` (Atelier 4) | — |
