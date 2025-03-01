
# Configuration et Lancement du Projet MySurvey REST API

Bienvenue dans le projet **MySurvey** ! Ce guide explique comment configurer, construire et exécuter votre projet Spring Boot sur votre machine locale. Il inclut également quelques conseils pratiques sur l'utilisation de Markdown pour rédiger des documents clairs et bien structurés.

---

## Table des matières

1. [Introduction](#introduction)
2. [Prérequis](#prérequis)
3. [Étapes de lancement](#étapes-de-lancement)
4. [Accès à l'application](#accès-à-lapplication)
5. [Notes supplémentaires](#notes-supplémentaires)
6. [Documentation Docker (Optionnel)](#documentation-docker-optionnel)
7. [Ressources Utiles](#ressources-utiles)

---

## Introduction

Ce guide explique comment configurer, construire et exécuter votre projet Spring Boot sur votre machine locale. Il fournit également des instructions détaillées pour utiliser Docker si vous choisissez cette option.

---

## Prérequis

Avant de commencer, assurez-vous d'avoir les outils suivants installés :

### 1. Java
Vérifiez que Java est installé sur votre système :
```bash
java -version
```

### 2. Maven
Assurez-vous que l'exécutable de Maven fonctionne correctement. Si vous utilisez le wrapper Maven fourni avec le projet, vérifiez sa version :
```bash
./mvnw -v
```

### 3. Docker (facultatif)
Si vous prévoyez d'utiliser Docker pour exécuter l'application, installez Docker et vérifiez qu'il fonctionne :
```bash
docker --version
docker-compose --version
```

---

## Étapes de lancement

### 1. Cloner ou télécharger le projet

Si vous n'avez pas encore le code source du projet sur votre machine, clonez-le depuis le dépôt Git ou téléchargez-le au format ZIP.

#### Via Git :
```bash
git clone https://github.com/GyumiBear/m2sid.git
cd m2sid
```

#### Via ZIP :
Téléchargez et extrayez l'archive depuis : [Lien vers le dépôt GitHub](https://github.com/GyumiBear/m2sid).

### 2. Construire le projet

Utilisez Maven pour construire le projet et installer toutes les dépendances nécessaires :
```bash
mvn clean install
```

### 3. Lancer l'application

Pour démarrer l'application Spring Boot directement à partir des sources, utilisez la commande suivante :
```bash
docker-compose up
```

Cela lancera l'application en chargeant le profil par défaut et toute configuration externe spécifiée.

---

## Accès à l'application

Une fois l'application démarrée, elle sera accessible via votre navigateur web ou un outil comme Postman à l'adresse suivante :

```
http://localhost:8080
```

---

## Notes supplémentaires

- **Docker** : Si vous travaillez avec Docker, assurez-vous que Docker est bien installé et fonctionnel sur votre machine. Vous pouvez ensuite utiliser `docker-compose` pour démarrer les services associés.
- **Logs** : En cas de problème, consultez les logs générés lors du démarrage pour plus d'informations. Ils peuvent être affichés en temps réel avec la commande suivante :
  ```bash
  docker-compose logs -f
  ```

---

## Documentation Docker (Optionnel)

Si vous choisissez d'utiliser Docker pour exécuter l'application, voici les étapes à suivre :

### 1. Construction des Images Docker

Construisez les images Docker pour l'application et la base de données :
```bash
docker-compose build
```

### 2. Lancement des Conteneurs

Démarrez les conteneurs avec la commande suivante :
```bash
docker-compose up
```

Les services suivants seront lancés :
- **Application Spring Boot** : Accessible à `http://localhost:8080`
- **Base de données PostgreSQL** : Accessible à `jdbc:postgresql://localhost:5432/mysurvey`

### 3. Arrêt des Conteneurs

Pour arrêter les conteneurs, utilisez la commande :
```bash
docker-compose down
```

---

## Ressources Utiles

- **Documentation Docker** : [https://hub.docker.com/repository/docker/gyumibear/m2sid-app/general](https://hub.docker.com/repository/docker/gyumibear/m2sid-app/general)
- **Base de Données Docker** : [https://hub.docker.com/repository/docker/gyumibear/m2sid-db/general](https://hub.docker.com/repository/docker/gyumibear/m2sid-db/general)

---

## Support et Contribuer

Si vous rencontrez des problèmes ou souhaitez contribuer au projet, n'hésitez pas à ouvrir une issue ou une pull request sur notre dépôt GitHub.

Merci d'utiliser **MySurvey** !
