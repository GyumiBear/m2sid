# Documentation du Projet Spring Boot

---

## Introduction

Ce guide explique comment configurer, construire et exécuter votre projet Spring Boot sur votre machine locale. Il inclut également quelques conseils pratiques sur l'utilisation de Markdown pour rédiger des documents clairs et bien structurés.

## Prérequis

Avant de commencer, assurez-vous d'avoir les outils suivants installés :

- **Java** : Vérifiez que Java est installé sur votre système.
  ```bash
  java -version
  ```
- **Maven** : Assurez-vous que Maven est également installé.
  ```bash
  mvn -v
  ```
## Étapes de lancement

### 1. Cloner ou télécharger le projet

Si vous n'avez pas encore le code source du projet sur votre machine, clonez-le depuis le dépôt Git ou téléchargez-le au formet Zip.

```bash
    git clone https://github.com/GyumiBear/m2sid
    cd m2sid
```
### 2. Construire le projet

Utilisez Maven pour construire le projet et installer toutes les dépendances nécessaires :

```bash
    mvn clean install
```

### 3. Lancer l'application

pour démarrer l'application Spring Boot directement à partir des sources, utilisez la commande suivante :

```bash
    mvn spring-boot:run
```

Cela lancera l'application en chargeant le profil par défaut et toute configuration externe spécifiée.

---

## Accès à l'application

Une fois l'application démarrée, elle sera accessible via votre navigateur web ou un outil comme Postman à l'adresse suivante :

```
http://localhost:8080
```

---

## Notes Supplémentaires

- Si vous travaillez avec Docker, assurez-vous que Docker est bien installé et fonctionnel sur votre machine. Vous pouvez ensuite utiliser ```docker-compose``` pour démarrer les services associés.
- En cas de problème, consultez les logs générés lors du démarrage pour plus d'informations.