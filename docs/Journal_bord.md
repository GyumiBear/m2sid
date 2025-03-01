
# Journal de Bord - Projet MySurvey

---

## Introduction

Ce journal retrace les étapes de développement du projet **MySurvey**, une application Spring Boot pour la gestion de sondages. Il documente notre analyse initiale, les décisions stratégiques prises, les méthodologies adoptées, et les ajustements réalisés pour respecter le cahier des charges tout en assurant une collaboration efficace via Git.

---

## Étapes du projet

### Étape 1 : Analyse initiale du projet existant

- **Objectif** : Comprendre le fonctionnement initial de l’application avant toute modification.
- **Constat** :
  - Le projet fourni par le professeur manquait totalement de documentation (absence de README ou de spécifications explicites).
  - Les endpoints de l’application Spring Boot n’étaient pas documentés.
  - Le schéma de la base de données (fourni séparément) était notre seul point de départ pour comprendre les entités.
- **Actions** :
  - Examiné les fichiers `Controller` (par exemple, `SondageController`, `ParticipantController`) pour déduire les endpoints REST disponibles :
    - Exemples identifiés : `/api/sondage/`, `/api/participant/`, `/api/sondage/{id}/best`, `/api/sondage/{id}/maybe`.
  - Analysé le schéma de la base de données pour comprendre les entités et leurs relations :
    - Entités principales : `Sondage`, `Participant`, `DateSondage`, `DateSondee`, `Commentaire`.
    - Relations : `Sondage` 1-n `DateSondage`, `Participant` n-n `Sondage` via `DateSondee`, `Commentaire` lié à `Sondage` et `Participant`.
  - Exécuté une première commande `./mvnw clean test` pour évaluer l’état initial des tests (résultat : 165 tests, mais avec des échecs/erreurs).
- **Problèmes** :
  - Absence de documentation, rendant l’analyse chronophage et sujette à des interprétations.
  - Tests unitaires existants échouant (par exemple, erreurs SQL ou mocks mal configurés).
- **Décision** :
  - S’appuyer sur les fichiers source et le schéma comme base de travail.
  - Prioriser la création de tests pour valider les fonctionnalités et documenter au fur et à mesure.

---

### Étape 2 : Définition des objectifs et adoption de TDD

- **Objectif** : Établir une stratégie pour respecter le cahier des charges tout en améliorant le code existant.
- **Actions** :
  - Étudié le cahier des charges fourni :
    - Fonctionnalités principales : création de sondages, ajout de dates proposées, participation avec choix (`DISPONIBLE`, `PEUTETRE`), gestion de commentaires, calcul des meilleures dates (`best` : max `DISPONIBLE`, `maybe` : max `DISPONIBLE + PEUTETRE`).
    - Exigence : garantir un comportement cohérent même après clôture (par exemple, retourner une erreur explicite).
  - Décidé d’adopter une approche **Test-Driven Development (TDD)** :
    - Rédaction préalable des tests unitaires pour chaque service (`SondageService`, `DateSondeeService`, etc.) et des tests End-to-End (E2E) pour valider les flux complets.
    - Objectif : s’assurer que le cahier des charges est respecté sans présumer que le code initial est fonctionnel.
  - Planifié les tests :
    - Tests unitaires : Vérifier les méthodes individuellement (par exemple, `bestDate` retourne la date avec le plus de `DISPONIBLE`).
    - Tests E2E : Simuler un scénario utilisateur complet (création d’un sondage, ajout de dates, votes, clôture), comme dans `TestSondageEndToEnd`.
- **Stratégie TDD** :
  - Écrire des tests correspondant au comportement idéal défini par le cahier des charges.
  - Exécuter les tests pour identifier les lacunes ou bugs dans le code existant (par exemple, requêtes SQL incorrectes, gestion d’erreurs absente).
  - Modifier le code source itérativement jusqu’à ce que tous les tests passent, assurant ainsi la conformité aux exigences.
- **Problèmes anticipés** :
  - Code initial potentiellement incomplet ou erroné, nécessitant des ajustements importants.
  - Possibles ambiguïtés dans le cahier des charges à clarifier via les tests.
- **Décision** :
  - Commencer par les tests E2E pour couvrir les flux principaux, puis détailler avec des tests unitaires pour chaque composant.
  - Accepter des itérations multiples pour aligner le code sur les tests.

---

### Étape 3 : Mise en place d’un workflow de collaboration avec Gitflow

- **Objectif** : Définir une méthode de collaboration efficace pour l’équipe via Git, compatible avec une pipeline CI/CD obligatoire.
- **Actions** :
  - Recherché différents workflows Git pour trouver celui adapté à notre projet :
    - **GitHub Flow** : Simple, mais trop linéaire pour un projet avec CI/CD et releases.
    - **Feature Branch Workflow** : Flexible, mais manque de structure pour la gestion des versions.
    - **Gitflow** : Structuré avec branches dédiées, idéal pour projets avec pipeline CI/CD et déploiement progressif.
  - Adopté le **Gitflow Workflow** :
    - **Branche `main`** : Contient le code stable prêt pour la production. Mise à jour uniquement via des merges validés depuis `dev`.
    - **Branche `dev`** : Branche d’intégration où les développeurs consolident leurs contributions. Sert de bac à sable pour tester les nouvelles fonctionnalités avant production.
    - **Branches `feature/*`** : Sous-branches de `dev`, créées pour chaque nouvelle fonctionnalité ou correction (exemple : `feature/add-comment-endpoint`).
  - Processus de collaboration défini :
    1. Chaque développeur crée une branche feature depuis `dev` :
       ```bash
       git checkout -b feature/<nom-fonctionnalité> dev
       ```
    2. Réalise des commits locaux réguliers avec des messages descriptifs :
       ```bash
       git commit -m "Ajout endpoint pour récupérer les meilleures dates"
       ```
    3. Pousse ses changements vers GitHub :
       ```bash
       git push origin feature/<nom-fonctionnalité>
       ```
    4. Ouvre une Pull Request (PR) vers `dev` :
       - Quand la fonctionnalité est terminée ou si une aide est nécessaire sur un problème complexe.
       - La PR peut être enrichie par des commits supplémentaires après revue par les pairs.
       - Merge dans `dev` après validation par l’équipe (revue de code et passage des tests CI).
    5. Pull Request finale de `dev` vers `main` :
       - Consolidée après intégration de plusieurs features dans `dev`.
       - Validée par une revue et une exécution réussie de la pipeline CI/CD de `main`.
       - Résultat : `main` reflète une version stable avec les nouvelles fonctionnalités.

---

### Étape 4 : Rédaction des tests et identification des problèmes

- **Objectif** : Implémenter les tests unitaires et E2E pour valider le respect du cahier des charges et détecter les problèmes dans le code existant.
- **Actions** :
  - Créé une branche feature pour les tests : `git checkout -b feature/initial-tests dev`.
  - Rédigé des tests unitaires :
    - Pour `SondageService` : Tests de création, mise à jour, et suppression de sondages.
    - Pour `DateSondeeService` : Tests des méthodes `bestDate` et `maybeBestDate` pour vérifier les calculs de dates.
  - Rédigé un test E2E (`TestSondageEndToEnd`) :
    - Scénario : Création d’un sondage, ajout de dates, participation par deux utilisateurs, calcul des meilleures dates, clôture, et vérification du comportement post-clôture.
    - Exemple de test :
      ```java
      @Test
      void sondageTestEndToEnd() {
          // Création sondage, ajout dates, votes, vérification best/maybe, clôture
          assertEquals(1, bestDates.size()); // bestDate retourne 1 date
          assertEquals(2, maybeDates.size()); // maybeBestDate retourne 2 dates
          given().get("/api/sondage/{id}/best").then().statusCode(404); // Après clôture
      }
      ```
  - Exécuté les tests avec `./mvnw clean test -X -e > debug_log_full.txt`.
- **Problèmes** :
  - **TestSondageEndToEnd** :
    - Échec initial : `expected: <1> but was: <0>` pour `bestDates` (liste vide).
    - Cause : Requête SQL incorrecte dans `DateSondeeRepository.bestDate` (syntaxe `f1.max` sans alias).
  - **TestSondageEndToEnd** (après correction) :
    - Échec : `expected: <2> but was: <1>` pour `maybeDates` (ne comptait que `PEUTETRE`).
    - Cause : `maybeBestDate` n’incluait pas `DISPONIBLE + PEUTETRE`.
  - **TestSondageEndToEnd** (après correction) :
    - Échec : `expected: <404> but was: <500>` après clôture.
    - Cause : Pas de gestion de l’état `cloture` dans `DateSondeeService`.
  - **TestDateSondageService** :
    - Erreurs : `NullPointerException` dans `testBestDate` et `testMaybeBestDate` (lignes 120, 131).
    - Cause : Mock absent pour `SondageService.getById()`.
- **Décision** :
  - Corriger les bugs un par un en suivant les échecs des tests, en créant des PR séparées pour chaque correction (par exemple, `feature/fix-sql`, `feature/fix-cloture`).

---

### Étape 5 : Résolution des bugs et alignement au cahier des charges

- **Objectif** : Corriger les problèmes détectés par les tests pour faire passer les 165 tests.
- **Actions** :
  - Créé des branches feature pour chaque bug :
    - **`feature/fix-sql`** :
      - Corrigé `DateSondeeRepository.bestDate` avec un alias (`MAX(nb) AS max_nb`).
      - Modifié `maybeBestDate` pour inclure `DISPONIBLE + PEUTETRE` :
        ```sql
        SELECT d.date
        FROM (SELECT MAX(nb) AS max_nb 
              FROM (SELECT date_sondage_id, COUNT(choix) AS nb 
                    FROM date_sondee 
                    WHERE choix IN ('DISPONIBLE', 'PEUTETRE') 
                    GROUP BY date_sondage_id) f) f1,
        ...
        ```
      - Résultat : `maybeBestDate` retourne 2 dates, résolvant `expected: <2> but was: <1>`.
    - **`feature/fix-cloture`** :
      - Ajouté une vérification dans `DateSondeeService` :
        ```java
        Sondage sondage = sondageService.getById(id);
        if (sondage == null || Boolean.TRUE.equals(sondage.getCloture())) {
            throw new EntityNotFoundException("Sondage with id " + id + " not found or closed");
        }
        ```
      - Ajouté un gestionnaire d’exceptions dans `SondageController` :
        ```java
        @ExceptionHandler(EntityNotFoundException.class)
        @ResponseStatus(HttpStatus.NOT_FOUND)
        public ResponseEntity<String> handleEntityNotFound(EntityNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
        ```
      - Résultat : Retourne 404 après clôture, résolvant `expected: <404> but was: <500>`.
    - **`feature/fix-unit-tests`** :
      - Corrigé `TestDateSondageService` en mockant `SondageService.getById()` :
        ```java
        Sondage sondage = new Sondage();
        sondage.setCloture(false);
        when(sondageService.getById(anyLong())).thenReturn(sondage);
        ```
      - Résultat : Résolution des `NullPointerException` aux lignes 120 et 131.
  - Poussé chaque branche et créé des PR vers `dev` :
    ```bash
    git push origin feature/fix-sql
    ```
  - Validé les PR après passage des tests dans la pipeline CI/CD de `dev`.
  - Merge final de `dev` vers `main` après toutes les corrections.
- **Problèmes** :
  - Syntaxe SQL initiale mal formée nécessitant une compréhension approfondie des jointures.
  - Absence initiale de gestion des exceptions dans le controller.
  - Configuration des mocks oubliée dans les tests unitaires.
- **Décision** :
  - Intégrer chaque correction dans `dev` via des PR séparées pour faciliter la revue.
  - Tester la pipeline CI/CD de `main` après merge pour valider les images Docker.

---

### Étape 6 : Génération des rapports et finalisation de la documentation

- **Objectif** : Valider la stabilité du projet et fournir une documentation claire pour l’équipe et le professeur.
- **Actions** :
  - Exécuté les tests finaux :
    ```bash
    ./mvnw clean test
    ```
    - Résultat : `Tests run: 165, Failures: 0, Errors: 0, Skipped: 0`.
  - Ajouté le plugin `maven-surefire-report-plugin` au `pom.xml` :
    ```xml
    <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-surefire-report-plugin</artifactId>
      <version>3.2.5</version>
    </plugin>
    ```
  - Généré un rapport HTML des tests :
    ```bash
    ./mvnw surefire-report:report
    ```
    - Vérifié `target/site/surefire-report.html` : Rapport visuel confirmant le succès des 165 tests.
  - Mis à jour la documentation :
    - Créé un `README.md` avec instructions pour lancer l’application et générer les rapports.
    - Rédigé ce journal de bord pour détailler le processus.
  - Poussé les changements dans une branche `feature/documentation` et créé une PR vers `dev`.
- **Problèmes** :
  - Aucun majeur à ce stade, les tests passant tous.
- **Décision** :
  - Finaliser le merge dans `dev`, puis dans `main` après validation CI/CD.
  - Préparer une présentation pour le professeur avec le rapport HTML comme preuve de conformité.

---

## Résumé des décisions clés

- **Analyse** : Déduction des endpoints et de la structure des données à partir des fichiers `Controller` et du schéma, en l’absence de documentation initiale.
- **TDD** : Adoption d’une approche Test-Driven Development pour garantir que chaque fonctionnalité respecte le cahier des charges, avec priorisation des tests E2E suivis des tests unitaires.
- **Gitflow avec CI/CD** : Mise en place d’un workflow Gitflow structuré (`main`, `dev`, `feature/*`), soutenu par des pipelines CI/CD via GitHub Actions pour valider les tests (`dev`) et déployer des images Docker stables (`main`).

---

## Prochaines étapes

- Ajouter des fonctionnalités supplémentaires demandées par le professeur (par exemple, statistiques avancées sur les sondages).
- Optimiser les pipelines CI/CD pour réduire les temps de build des images Docker multi-plateformes.
- Étendre la documentation avec une liste complète des endpoints REST et un diagramme UML des entités.

---

## Réflexions

L’approche TDD a permis de transformer un projet initialement mal documenté et instable en une application conforme au cahier des charges, avec des tests robustes comme filet de sécurité. Le workflow Gitflow, combiné aux pipelines CI/CD, a structuré notre collaboration et assuré une progression sans régressions. La génération des rapports HTML offre une visualisation claire de nos progrès, essentielle pour valider notre travail auprès de l’équipe et du professeur.

---