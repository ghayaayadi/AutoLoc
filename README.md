# AutoLoc

Plateforme de gestion de location de véhicules multi-agences.

Projet réalisé dans le cadre du module UP ASI (Architecture des Systèmes d'Information) à ESPRIT.

## Objectifs du projet

- Gérer le parc de véhicules de plusieurs agences de location
- Gérer les clients, les réservations, les contrats et les paiements
- Suivre la maintenance des véhicules
- Exposer une API REST (Spring Boot) pour ces fonctionnalités

## Acteurs identifiés

- Client
- Agent d'agence
- Responsable d'agence (Manager)
- Administrateur

## Cas d'utilisation (première liste)

**Client**
- Consulter les véhicules disponibles
- Réserver un véhicule
- Annuler une réservation
- Payer une location

**Agent d'agence**
- Enregistrer un client
- Traiter une réservation
- Établir et faire signer un contrat
- Enregistrer le retour d'un véhicule

**Responsable d'agence (Manager)**
- Gérer les véhicules de l'agence
- Planifier la maintenance
- Superviser les agents et les réservations

**Administrateur**
- Gérer les agences
- Gérer les comptes utilisateurs
- Configurer la plateforme

## Technologies

Java 17, Maven, Spring Boot, Spring Data JPA, MySQL, Lombok, Git/GitHub, Postman, IntelliJ IDEA.

## Auteur

Ghaya Ayadi
