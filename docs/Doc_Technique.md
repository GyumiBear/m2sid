# Document Technique - API MySurvey

## Table des mati�res
1. [API Commentaire](#api-commentaire)
2. [API DateSondage](#api-datesondage)
3. [API Participant](#api-participant)
4. [API Sondage](#api-sondage)
5. [Mod�les](#mod�les)
6. [Pipeline](#pipeline)
7. [Tests](#tests)
8. [Docker](#docker)

---

## API Commentaire

### Mettre � jour un commentaire
**M�thode :** `PUT`  
**URL :** `/api/commentaire/{id}`  
**Description :** Met � jour un commentaire existant.

### Supprimer un commentaire
**M�thode :** `DELETE`  
**URL :** `/api/commentaire/{id}`  
**Description :** Supprime un commentaire sp�cifique.

---

## API DateSondage

### Supprimer un objet DateSondage
**M�thode :** `DELETE`  
**URL :** `/api/date/{id}`  
**Description :** Supprime une date de sondage sp�cifique.

### Cr�er une nouvelle participation
**M�thode :** `POST`  
**URL :** `/api/date/{id}/participer`  
**Description :** Ajoute une nouvelle participation pour une date donn�e.

---

## API Participant

### R�cup�rer tous les participants
**M�thode :** `GET`  
**URL :** `/api/participant/`  
**Description :** Renvoie la liste de tous les participants.

### Cr�er un nouveau participant
**M�thode :** `POST`  
**URL :** `/api/participant/`  
**Description :** Cr�e un nouveau participant.

### R�cup�rer un participant
**M�thode :** `GET`  
**URL :** `/api/participant/{id}`  
**Description :** Renvoie les d�tails d'un participant sp�cifique.

### Mettre � jour un participant
**M�thode :** `PUT`  
**URL :** `/api/participant/{id}`  
**Description :** Met � jour les informations d'un participant.

### Supprimer un participant
**M�thode :** `DELETE`  
**URL :** `/api/participant/{id}`  
**Description :** Supprime un participant sp�cifique.

---

## API Sondage

### R�cup�rer tous les sondages
**M�thode :** `GET`  
**URL :** `/api/sondage/`  
**Description :** Renvoie la liste de tous les sondages.

### Cr�er un nouveau sondage
**M�thode :** `POST`  
**URL :** `/api/sondage/`  
**Description :** Cr�e un nouveau sondage.

### R�cup�rer un sondage
**M�thode :** `GET`  
**URL :** `/api/sondage/{id}`  
**Description :** Renvoie les d�tails d'un sondage sp�cifique.

### Mettre � jour un sondage
**M�thode :** `PUT`  
**URL :** `/api/sondage/{id}`  
**Description :** Met � jour les informations d'un sondage.

### Supprimer un sondage
**M�thode :** `DELETE`  
**URL :** `/api/sondage/{id}`  
**Description :** Supprime un sondage sp�cifique.

### R�cup�rer les meilleures dates d�un sondage
**M�thode :** `GET`  
**URL :** `/api/sondage/{id}/best`  
**Description :** Renvoie les meilleures dates d'un sondage.

### R�cup�rer tous les commentaires d�un sondage
**M�thode :** `GET`  
**URL :** `/api/sondage/{id}/commentaires`  
**Description :** Renvoie tous les commentaires li�s � un sondage.

### Ajouter un commentaire � un sondage
**M�thode :** `POST`  
**URL :** `/api/sondage/{id}/commentaires`  
**Description :** Ajoute un commentaire � un sondage.

### R�cup�rer toutes les dates d�un sondage
**M�thode :** `GET`  
**URL :** `/api/sondage/{id}/dates`  
**Description :** Renvoie toutes les dates li�es � un sondage.

### Ajouter une date � un sondage
**M�thode :** `POST`  
**URL :** `/api/sondage/{id}/dates`  
**Description :** Ajoute une date � un sondage.

### R�cup�rer les �ventuelles meilleures dates d�un sondage
**M�thode :** `GET`  
**URL :** `/api/sondage/{id}/maybe`  
**Description :** Renvoie les �ventuelles meilleures dates d'un sondage.

---

## Mod�les

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

Notre pipeline CI/CD est con�u pour garantir une int�gration et une livraison continues des fonctionnalit�s dans notre projet. Il est bas� sur **GitHub Actions** et se d�compose en deux workflows principaux : un pour la branche `dev` (CI) et un pour la branche `main` (CD). Voici une explication d�taill�e de chaque workflow.

---

## 1. **Pipeline CI pour la branche `dev`**

### Fichier : `.github/workflows/ci-cd-dev.yml`

Ce pipeline est d�clench� lorsqu'une pull request (MR) est cr��e ou mise � jour sur la branche `dev`. Son objectif principal est de v�rifier que le code est correct avant d'�tre fusionn� dans la branche de d�veloppement.

### �tapes du Workflow :

1. **V�rification du Code**
   - Action utilis�e : `actions/checkout@v4`
   - Cette �tape clone le d�p�t Git pour que les �tapes suivantes puissent y acc�der.

2. **Pr�paration du JDK 17**
   - Action utilis�e : `actions/setup-java@v4`
   - Configuration du JDK 17 pour ex�cuter les t�ches Maven n�cessaires.

3. **Suppression de l'Ancien Target et Installation des D�pendances**
   - Commande : `mvn clean install`
   - Cette commande nettoie les fichiers g�n�r�s pr�c�demment (`target`) et installe toutes les d�pendances n�cessaires au projet.

4. **V�rification de la Compatibilit� des Plugins**
   - Commande : `mvn verify`
   - V�rifie que tous les plugins utilis�s dans le projet sont compatibles avec la configuration actuelle.

5. **V�rification via des Tests**
   - Commande : `mvn test`
   - Ex�cute tous les tests unitaires et d'int�gration pour s'assurer que le code fonctionne comme pr�vu.

---

## 2. **Pipeline CD pour la branche `main`**

### Fichier : `.github/workflows/ci-cd-main.yml`

Ce pipeline est d�clench� lorsqu'une pull request (MR) est cr��e ou mise � jour sur la branche `main`. Son objectif principal est de construire et pousser les images Docker n�cessaires pour le d�ploiement en production.

### �tapes du Workflow :

1. **Checkout du Code**
   - Action utilis�e : `actions/checkout@v4`
   - Clone le d�p�t Git pour que les �tapes suivantes puissent y acc�der.

2. **Configuration de QEMU pour l'�mulation Multi-Plateforme**
   - Action utilis�e : `docker/setup-qemu-action@v2`
   - Configure QEMU pour permettre la construction d'images Docker pour diff�rentes architectures (AMD64 et ARM64).

3. **Configuration de Docker Buildx**
   - Action utilis�e : `docker/setup-buildx-action@v2`
   - Installe et configure Docker Buildx, un outil pour la construction multi-architecture.

4. **Connexion � Docker Hub**
   - Action utilis�e : `docker/login-action@v2`
   - Se connecte � Docker Hub en utilisant les informations d'identification stock�es dans les secrets GitHub (`DOCKERHUB_USERNAME` et `DOCKERHUB_TOKEN`).

5. **Gestion de l'Image PostgreSQL**

   - **Tirer l'Image Officielle PostgreSQL pour AMD64**
      - Commande : `docker pull --platform linux/amd64 postgres:latest`
      - T�l�charge l'image officielle PostgreSQL pour l'architecture AMD64.

   - **Retaguer et Pousser l'Image PostgreSQL pour AMD64**
      - Commandes :
        ```bash
        docker tag postgres:latest gyumibear/m2sid-db:amd64
        docker push gyumibear/m2sid-db:amd64
        ```
      - Retague et pousse l'image vers Docker Hub avec un nom personnalis�.

   - **Construire et Pousser l'Image PostgreSQL pour ARM64**
      - Commandes :
        ```bash
        docker buildx build --platform linux/arm64 --push -t gyumibear/m2sid-db:arm64 .
        ```
      - Construit et pousse une version de l'image PostgreSQL pour l'architecture ARM64.

6. **Gestion de l'Image Alpine (Exemple suppl�mentaire)**

   - **Tirer l'Image Officielle Alpine pour ARM64**
      - Commande : `docker pull --platform linux/arm64 alpine:latest`
      - T�l�charge l'image Alpine pour l'architecture ARM64.

   - **Retaguer et Pousser l'Image Alpine pour ARM64**
      - Commandes :
        ```bash
        docker tag alpine:latest gyumibear/m2sid-db:arm64
        docker push gyumibear/m2sid-db:arm64
        ```
      - Retague et pousse l'image Alpine vers Docker Hub.

7. **Construction et Publication de l'Application Spring Boot**

   - **Pour AMD64**
      - Action utilis�e : `docker/build-push-action@v3`
      - Construit et pousse l'image Docker de l'application Spring Boot pour l'architecture AMD64.
      - Tags : `gyumibear/m2sid-app:amd64`

   - **Pour ARM64**
      - Action utilis�e : `docker/build-push-action@v3`
      - Construit et pousse l'image Docker de l'application Spring Boot pour l'architecture ARM64.
      - Tags : `gyumibear/m2sid-app:arm64`

---

## R�sum�

### Pourquoi GitHub Actions ?
Nous avons choisi GitHub Actions car nous utilisons d�j� GitHub pour le versionnage de notre code. Cela nous permet d'avoir une solution int�gr�e pour notre pipeline CI/CD.

### Objectifs Principaux :
1. **Branche `dev` (CI)** : Valider le code avant qu'il soit fusionn� dans la branche de d�veloppement.
2. **Branche `main` (CD)** : Construire et d�ployer automatiquement les images Docker n�cessaires pour les environnements de production.

### Avantages :
- **Multi-architecture** : Nos images Docker sont construites pour plusieurs architectures (AMD64 et ARM64), ce qui garantit la compatibilit� avec diff�rents environnements.
- **Automatisation** : Le pipeline r�duit consid�rablement les erreurs humaines et acc�l�re le processus de d�ploiement.
- **S�curit�** : Les tests automatis�s garantissent que le code est fonctionnel et s�curis� avant toute publication.

---

## Ressources Docker

Voici les liens vers les images Docker publi�es :

- **Spring Boot Application** : [https://hub.docker.com/repository/docker/gyumibear/m2sid-app/general](https://hub.docker.com/repository/docker/gyumibear/m2sid-app/general)
- **PostgreSQL Database** : [https://hub.docker.com/repository/docker/gyumibear/m2sid-db/general](https://hub.docker.com/repository/docker/gyumibear/m2sid-db/general)

Ces images sont mises � jour automatiquement lors de chaque d�ploiement sur la branche `main`.
# Tests

## Tests Unitaires

Les tests unitaires sont essentiels pour garantir que les unit�s individuelles de code fonctionnent comme pr�vu. Nous utilisons **JUnit 5** et **Mockito** pour �crire et ex�cuter nos tests unitaires.

### Processus :
- Utilisation de `@Mock` pour cr�er des repr�sentations factices des classes.
- Injection de ces simulacres via `@InjectMocks`.
- D�finition du comportement attendu avec `when(...).thenReturn(...)`.
- Appel de la m�thode test�e et v�rification avec `verify(..., times(...)).method(...)`.

### Exclusions :
- Classes du dossier `configurations`.
- Fichier `MySurveyApplication.java`.

### Statut :
- Coverage actuel : **100%** (version 1.0.0).

---

## Tests E2E

Les tests E2E (End-to-End) assurent l'int�grit� globale de l'application en simulant le parcours complet d'un utilisateur. Nous utilisons **Swagger** pour faciliter la cr�ation et la validation des tests E2E, combin� avec **JUnit 5** et **Rest Assured**.

### Probl�me identifi� :
- M�thodes de mise � jour dans `SondageService.java` et `CommentaireService.java` ne prenaient pas en compte les propri�t�s des objets pass�s en param�tre.
- Solution : Modification pour inclure la mise � jour des propri�t�s.

---

# Docker

Le projet inclut un fichier `Dockerfile` et un `docker-compose.yml` qui cr�ent deux images Docker. La documentation est disponible sur le **Hub Docker**.