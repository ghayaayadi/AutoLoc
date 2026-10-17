# Atelier 4 — Note : IoC, injection de dépendances et couche Service

Projet : AutoLoc (`autoloc-api`) — UP ASI, ESPRIT 2026-27 — Auteur : Ghaya Ayadi

## A. Modes d'injection comparés

| Critère | Injection par constructeur (`@RequiredArgsConstructor` + `final`) | Injection par attribut (`@Autowired` sur le champ) |
|---|---|---|
| Champ `final` possible ? | Oui | Non (le champ est modifié par réflexion après construction) |
| Dépendance visible dans la signature de la classe ? | Oui, c'est un paramètre du constructeur | Non, elle est cachée dans le corps de la classe |
| Utilisable avec `new` hors conteneur sans dépendance `null` ? | Oui : `new ContratServiceImpl(repo)` oblige à fournir le repository | Non : `new ContratServiceImpl()` donne un `contratRepository` à `null` (NPE à l'appel) |
| Signalé par SonarQube for IDE ? | Non | Oui : java:S6813 « Field dependency injection should be avoided » (Remove this field injection and use constructor injection instead) |

Conclusion : convention AutoLoc = injection par constructeur. L'objet ne peut jamais exister dans un état incomplet.

## B. Services

| Service | Dépendances injectées | Mode | Justification |
|---|---|---|---|
| AgenceServiceImpl | IAgenceRepository | Constructeur | Une seule dépendance, `final`, typée par interface ; Spring utilise le constructeur unique sans `@Autowired`. |
| EmployeServiceImpl | IEmployeRepository | Constructeur | Idem. |
| VehiculeServiceImpl | IVehiculeRepository | Constructeur | Idem. |
| EquipementServiceImpl | IEquipementRepository | Constructeur | Idem. |
| ClientServiceImpl | IClientRepository | Constructeur | Idem. |
| ReservationServiceImpl | IReservationRepository | Constructeur | Idem. |
| ContratServiceImpl | IContratRepository | Constructeur | Idem. |
| PaiementServiceImpl | IPaiementRepository | Constructeur | Idem ; service en lecture seule (`findById`, `findAll`). |
| MaintenanceServiceImpl | IMaintenanceRepository | Constructeur | Idem. |

Règles de gestion implémentées (selon les attributs réels du modèle) :

- Agence : `nom`, `ville`, `adresse` non vides.
- Employe : `nom`, `prenom` non vides, `role` renseigné.
- Vehicule : `immatriculation`, `marque`, `modele` non vides ; `tarifJournalier` renseigné et >= 0.
- Equipement : `libelle` non vide (le modèle n'a pas d'attribut quantité).
- Client : `nom`, `prenom`, `email`, `numPermis` non vides.
- Reservation : dates renseignées, `dateFin` non antérieure à `dateDebut`.
- Contrat : `montantTotal` renseigné et >= 0 ; la suppression d'un contrat supprime ses paiements (`cascade = ALL`, `orphanRemoval = true` sur `Contrat.paiements`).
- Paiement : lecture seule, les écritures passent par le Contrat.
- Maintenance : `dateDebut` renseignée, `dateFin` (facultative) non antérieure à `dateDebut` (le modèle n'a pas d'attribut coût).

## C. Messages d'erreur du conteneur

**Message 1 — `ContratController` ne trouve pas de bean `IContratService`**
- Exception : `NoSuchBeanDefinitionException` (enveloppée dans `UnsatisfiedDependencyException`). Bean concerné : `IContratService`, demandé par le paramètre 0 du constructeur de `ContratController`.
- Causes probables côté `ContratServiceImpl` : (1) l'annotation `@Service` est oubliée ; (2) la classe est placée en dehors du package racine `tn.esprit.autoloc`, donc non détectée par le scan.
- Correction : ajouter `@Service`, ou déplacer la classe sous `tn.esprit.autoloc`.

**Message 2 — deux beans pour `NotificationController` (`emailNotificateur`, `smsNotificateur`)**
- Exception : `NoUniqueBeanDefinitionException`. Deux beans du même type, Spring ne sait pas lequel choisir.
- Correction 1 : `@Primary` sur la **classe** du bean par défaut (par exemple `EmailNotificateur`).
- Correction 2 : `@Qualifier("smsNotificateur")` sur le **paramètre du constructeur** du contrôleur, pour un choix explicite.

**Message 3 — cycle `clientServiceImpl` <-> `reservationServiceImpl`**
- Exception : `BeanCurrentlyInCreationException` (dépendance circulaire).
- Pourquoi c'est insoluble avec le constructeur : pour construire A il faut déjà B, et pour construire B il faut déjà A ; aucun des deux ne peut être instancié en premier, et les champs `final` interdisent de renseigner la dépendance après coup.
- Piste de conception : extraire la responsabilité commune dans un troisième service (ou faire dépendre les deux services des repositories seulement), pour que les dépendances soient à sens unique.

## D. Anomalies SonarQube for IDE

| Règle | Explication | Correction apportée |
|---|---|---|
| java:S6813 | Injection par attribut (`@Autowired` sur le champ) dans `ContratServiceImpl` : dépendance cachée, objet créable dans un état invalide, tests plus difficiles, incompatible avec un champ `final`. | Retour à l'injection par constructeur (`@RequiredArgsConstructor` + champ `final`). |
| java:S1128 | Import inutilisé `lombok.RequiredArgsConstructor` (apparu quand j'ai retiré `@RequiredArgsConstructor` pour le test de l'étape 2) ; même règle pour les imports inutilisés des entités (`ArrayList`, `List`, `HashSet`, `Set`). | `@RequiredArgsConstructor` restauré ; imports inutilisés supprimés dans `Employe`, `Paiement`, `Reservation`, `Maintenance`, `Vehicule`, `Equipement`. |
| java:S112 | Lever `RuntimeException` générique dans `findById` : l'appelant ne peut pas la distinguer d'une autre erreur. | Utilisation de `ResourceNotFoundException`. |

## E. Questions de compréhension

1. **Où est le `new` de `ContratServiceImpl` et qui l'exécute ?** Il n'est écrit nulle part dans notre code : c'est le conteneur Spring (`ApplicationContext`) qui appelle le constructeur au démarrage, après avoir détecté `@Service` par le scan et créé le proxy de `IContratRepository` à lui fournir.
2. **Pourquoi un contrôleur dépendra-t-il de `IContratService` et non de `ContratServiceImpl` ?** Pour dépendre d'une abstraction : on peut changer ou remplacer l'implémentation (autre version, double de test) sans toucher au contrôleur, ce qui réduit le couplage.
3. **Pourquoi un service singleton doit-il rester sans état ?** Une seule instance est partagée par tous les threads (requêtes simultanées). Exemple de bug : un attribut `contratCourant` modifié par la requête A puis écrasé par la requête B ; A traite alors le contrat de B.
4. **Pourquoi `update` charge-t-il l'entité existante avant `save` ?** Passer l'objet reçu directement à `save` écraserait toutes les colonnes (champs non fournis mis à `null`) et, avec `orphanRemoval`, une collection vide pourrait supprimer les paiements. On charge, on recopie les attributs modifiables, puis on sauvegarde.
5. **Différences.**
   - `@Component` se pose sur une classe qu'on écrit ; `@Bean` se pose sur une méthode d'une classe `@Configuration`, pour une classe qu'on ne peut pas annoter (bibliothèque, JDK) ou une construction particulière.
   - `@Primary` est posé sur la classe candidate par défaut ; `@Qualifier` est posé à l'injection pour choisir explicitement un bean.
   - `IPaiementService` n'expose ni création, ni modification, ni suppression : le paiement est l'enfant d'une composition avec le Contrat, il n'est modifié qu'à travers son parent (cascade et `orphanRemoval`).
