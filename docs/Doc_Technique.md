# Document Technique - API MySurvey

## Table des matières
1. [API Commentaire](#api-commentaire)
2. [API DateSondage](#api-datesondage)
3. [API Participant](#api-participant)
4. [API Sondage](#api-sondage)
5. [Modèles](#modèles)
6. [Pipeline](#pipeline)
7. [Tests](#tests)
8. [Docker](#docker)

---

## API Commentaire

### Mettre à jour un commentaire
**Méthode :** `PUT`  
**URL :** `/api/commentaire/{id}`  
**Description :** Met à jour un commentaire existant.

### Supprimer un commentaire
**Méthode :** `DELETE`  
**URL :** `/api/commentaire/{id}`  
**Description :** Supprime un commentaire spécifique.

---

## API DateSondage

### Supprimer un objet DateSondage
**Méthode :** `DELETE`  
**URL :** `/api/date/{id}`  
**Description :** Supprime une date de sondage spécifique.

### Créer une nouvelle participation
**Méthode :** `POST`  
**URL :** `/api/date/{id}/participer`  
**Description :** Ajoute une nouvelle participation pour une date donnée.

---

## API Participant

### Récupérer tous les participants
**Méthode :** `GET`  
**URL :** `/api/participant/`  
**Description :** Renvoie la liste de tous les participants.

### Créer un nouveau participant
**Méthode :** `POST`  
**URL :** `/api/participant/`  
**Description :** Crée un nouveau participant.

### Récupérer un participant
**Méthode :** `GET`  
**URL :** `/api/participant/{id}`  
**Description :** Renvoie les détails d'un participant spécifique.

### Mettre à jour un participant
**Méthode :** `PUT`  
**URL :** `/api/participant/{id}`  
**Description :** Met à jour les informations d'un participant.

### Supprimer un participant
**Méthode :** `DELETE`  
**URL :** `/api/participant/{id}`  
**Description :** Supprime un participant spécifique.

---

## API Sondage

### Récupérer tous les sondages
**Méthode :** `GET`  
**URL :** `/api/sondage/`  
**Description :** Renvoie la liste de tous les sondages.

### Créer un nouveau sondage
**Méthode :** `POST`  
**URL :** `/api/sondage/`  
**Description :** Crée un nouveau sondage.

### Récupérer un sondage
**Méthode :** `GET`  
**URL :** `/api/sondage/{id}`  
**Description :** Renvoie les détails d'un sondage spécifique.

### Mettre à jour un sondage
**Méthode :** `PUT`  
**URL :** `/api/sondage/{id}`  
**Description :** Met à jour les informations d'un sondage.

### Supprimer un sondage
**Méthode :** `DELETE`  
**URL :** `/api/sondage/{id}`  
**Description :** Supprime un sondage spécifique.

### Récupérer les meilleures dates d’un sondage
**Méthode :** `GET`  
**URL :** `/api/sondage/{id}/best`  
**Description :** Renvoie les meilleures dates d'un sondage.

### Récupérer tous les commentaires d’un sondage
**Méthode :** `GET`  
**URL :** `/api/sondage/{id}/commentaires`  
**Description :** Renvoie tous les commentaires liés à un sondage.

### Ajouter un commentaire à un sondage
**Méthode :** `POST`  
**URL :** `/api/sondage/{id}/commentaires`  
**Description :** Ajoute un commentaire à un sondage.

### Récupérer toutes les dates d’un sondage
**Méthode :** `GET`  
**URL :** `/api/sondage/{id}/dates`  
**Description :** Renvoie toutes les dates liées à un sondage.

### Ajouter une date à un sondage
**Méthode :** `POST`  
**URL :** `/api/sondage/{id}/dates`  
**Description :** Ajoute une date à un sondage.

### Récupérer les éventuelles meilleures dates d’un sondage
**Méthode :** `GET`  
**URL :** `/api/sondage/{id}/maybe`  
**Description :** Renvoie les éventuelles meilleures dates d'un sondage.

---

## Modèles

### CommentaireDto
```json
{
    "commentaire": "string",
    "commentaireId": "integer",
    "participant": "integer"
}
```
### DateSondageDto
```json
{
"date": "string",
"dateSondageId": "integer"
}
```
### DateSondeeDto
```json
{
"choix": "string",
"dateSdonneeId": "integer",
"participant": "integer"
}
```
### ParticipantDto
```json
{
"nom": "string",
"participants": "integer",
"prenom": "string"
}
```
### SondageDto
```json
{
"cloture": "boolean",
"createdBy": "integer",
"description": "string",
"fin": "string (date)",
"nom": "string",
"sondageId": "integer"
}
```

# Pipeline
# Explication de notre Pipeline CI/CD

Notre pipeline CI/CD est conçu pour garantir une intégration et une livraison continues des fonctionnalités dans notre projet. Il est basé sur **GitHub Actions** et se décompose en deux workflows principaux : un pour la branche `dev` (CI) et un pour la branche `main` (CD). Voici une explication détaillée de chaque workflow.

---

## 1. **Pipeline CI pour la branche `dev`**

### Fichier : `.github/workflows/ci-cd-dev.yml`

Ce pipeline est déclenché lorsqu'une pull request (MR) est créée ou mise à jour sur la branche `dev`. Son objectif principal est de vérifier que le code est correct avant d'être fusionné dans la branche de développement.

### Étapes du Workflow :

1. **Vérification du Code**
   - Action utilisée : `actions/checkout@v4`
   - Cette étape clone le dépôt Git pour que les étapes suivantes puissent y accéder.

2. **Préparation du JDK 17**
   - Action utilisée : `actions/setup-java@v4`
   - Configuration du JDK 17 pour exécuter les tâches Maven nécessaires.

3. **Suppression de l'Ancien Target et Installation des Dépendances**
   - Commande : `mvn clean install`
   - Cette commande nettoie les fichiers générés précédemment (`target`) et installe toutes les dépendances nécessaires au projet.

4. **Vérification de la Compatibilité des Plugins**
   - Commande : `mvn verify`
   - Vérifie que tous les plugins utilisés dans le projet sont compatibles avec la configuration actuelle.

5. **Vérification via des Tests**
   - Commande : `mvn test`
   - Exécute tous les tests unitaires et d'intégration pour s'assurer que le code fonctionne comme prévu.

---

## 2. **Pipeline CD pour la branche `main`**

### Fichier : `.github/workflows/ci-cd-main.yml`

Ce pipeline est déclenché lorsqu'une pull request (MR) est créée ou mise à jour sur la branche `main`. Son objectif principal est de construire et pousser les images Docker nécessaires pour le déploiement en production.

### Étapes du Workflow :

1. **Checkout du Code**
   - Action utilisée : `actions/checkout@v4`
   - Clone le dépôt Git pour que les étapes suivantes puissent y accéder.

2. **Configuration de QEMU pour l'Émulation Multi-Plateforme**
   - Action utilisée : `docker/setup-qemu-action@v2`
   - Configure QEMU pour permettre la construction d'images Docker pour différentes architectures (AMD64 et ARM64).

3. **Configuration de Docker Buildx**
   - Action utilisée : `docker/setup-buildx-action@v2`
   - Installe et configure Docker Buildx, un outil pour la construction multi-architecture.

4. **Connexion à Docker Hub**
   - Action utilisée : `docker/login-action@v2`
   - Se connecte à Docker Hub en utilisant les informations d'identification stockées dans les secrets GitHub (`DOCKERHUB_USERNAME` et `DOCKERHUB_TOKEN`).

5. **Gestion de l'Image PostgreSQL**

   - **Tirer l'Image Officielle PostgreSQL pour AMD64**
      - Commande : `docker pull --platform linux/amd64 postgres:latest`
      - Télécharge l'image officielle PostgreSQL pour l'architecture AMD64.

   - **Retaguer et Pousser l'Image PostgreSQL pour AMD64**
      - Commandes :
        ```bash
        docker tag postgres:latest gyumibear/m2sid-db:amd64
        docker push gyumibear/m2sid-db:amd64
        ```
      - Retague et pousse l'image vers Docker Hub avec un nom personnalisé.

   - **Construire et Pousser l'Image PostgreSQL pour ARM64**
      - Commandes :
        ```bash
        docker buildx build --platform linux/arm64 --push -t gyumibear/m2sid-db:arm64 .
        ```
      - Construit et pousse une version de l'image PostgreSQL pour l'architecture ARM64.

6. **Gestion de l'Image Alpine (Exemple supplémentaire)**

   - **Tirer l'Image Officielle Alpine pour ARM64**
      - Commande : `docker pull --platform linux/arm64 alpine:latest`
      - Télécharge l'image Alpine pour l'architecture ARM64.

   - **Retaguer et Pousser l'Image Alpine pour ARM64**
      - Commandes :
        ```bash
        docker tag alpine:latest gyumibear/m2sid-db:arm64
        docker push gyumibear/m2sid-db:arm64
        ```
      - Retague et pousse l'image Alpine vers Docker Hub.

7. **Construction et Publication de l'Application Spring Boot**

   - **Pour AMD64**
      - Action utilisée : `docker/build-push-action@v3`
      - Construit et pousse l'image Docker de l'application Spring Boot pour l'architecture AMD64.
      - Tags : `gyumibear/m2sid-app:amd64`

   - **Pour ARM64**
      - Action utilisée : `docker/build-push-action@v3`
      - Construit et pousse l'image Docker de l'application Spring Boot pour l'architecture ARM64.
      - Tags : `gyumibear/m2sid-app:arm64`

---

## Résumé

### Pourquoi GitHub Actions ?
Nous avons choisi GitHub Actions car nous utilisons déjà GitHub pour le versionnage de notre code. Cela nous permet d'avoir une solution intégrée pour notre pipeline CI/CD.

### Objectifs Principaux :
1. **Branche `dev` (CI)** : Valider le code avant qu'il soit fusionné dans la branche de développement.
2. **Branche `main` (CD)** : Construire et déployer automatiquement les images Docker nécessaires pour les environnements de production.

### Avantages :
- **Multi-architecture** : Nos images Docker sont construites pour plusieurs architectures (AMD64 et ARM64), ce qui garantit la compatibilité avec différents environnements.
- **Automatisation** : Le pipeline réduit considérablement les erreurs humaines et accélère le processus de déploiement.
- **Sécurité** : Les tests automatisés garantissent que le code est fonctionnel et sécurisé avant toute publication.

---

## Ressources Docker

Voici les liens vers les images Docker publiées :

- **Spring Boot Application** : [https://hub.docker.com/repository/docker/gyumibear/m2sid-app/general](https://hub.docker.com/repository/docker/gyumibear/m2sid-app/general)
- **PostgreSQL Database** : [https://hub.docker.com/repository/docker/gyumibear/m2sid-db/general](https://hub.docker.com/repository/docker/gyumibear/m2sid-db/general)

Ces images sont mises à jour automatiquement lors de chaque déploiement sur la branche `main`.
# Tests

## Tests Unitaires

Les tests unitaires sont essentiels pour garantir que les unités individuelles de code fonctionnent comme prévu. Nous utilisons **JUnit 5** et **Mockito** pour écrire et exécuter nos tests unitaires.

### Processus :
- Utilisation de `@Mock` pour créer des représentations factices des classes.
- Injection de ces simulacres via `@InjectMocks`.
- Définition du comportement attendu avec `when(...).thenReturn(...)`.
- Appel de la méthode testée et vérification avec `verify(..., times(...)).method(...)`.

### Exclusions :
- Classes du dossier `configurations`.
- Fichier `MySurveyApplication.java`.

### Statut :
- Coverage actuel : **100%** (version 1.0.0).

---

## Tests E2E

Les tests E2E (End-to-End) assurent l'intégrité globale de l'application en simulant le parcours complet d'un utilisateur. Nous utilisons **Swagger** pour faciliter la création et la validation des tests E2E, combiné avec **JUnit 5** et **Rest Assured**.

### Problème identifié :
- Méthodes de mise à jour dans `SondageService.java` et `CommentaireService.java` ne prenaient pas en compte les propriétés des objets passés en paramètre.
- Solution : Modification pour inclure la mise à jour des propriétés.

---

# Docker

Le projet inclut un fichier `Dockerfile` et un `docker-compose.yml` qui créent deux images Docker. La documentation est disponible sur le **Hub Docker**.