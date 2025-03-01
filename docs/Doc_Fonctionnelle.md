# Document Fonctionnel : Architecture Docker pour une API REST Spring Boot

## 1. Introduction
Ce document décrit l'architecture Docker utilisée pour déployer une application REST API développée avec Spring Boot.
L'application repose sur une base de données PostgreSQL et prend en charge deux architectures matérielles : AMD64 (WINDOWS) et ARM64 (LINUX).


Le fichier `docker-compose.yml` définit quatre services principaux :
- **db** : Base de données PostgreSQL pour les systèmes AMD64.
- **app** : Application Spring Boot pour les systèmes AMD64.
- **db-arm** : Base de données PostgreSQL pour les systèmes ARM64.
- **app-arm** : Application Spring Boot pour les systèmes ARM64.

---

## 2. Description des Services

### 2.1 Service `db` (Base de données PostgreSQL pour AMD64)
- **Image Docker** : `gyumibear/m2sid-db:amd64`
- **Plateforme** : Linux/AMD64
- **Ports exposés** :
    - Port hôte `5432` ? Port conteneur `5432` (port standard PostgreSQL)
- **Variables d'environnement** :
    - `POSTGRES_DB`: Nom de la base de données (`mydatabase`)
    - `POSTGRES_USER`: Utilisateur de la base de données (`babacar`)
    - `POSTGRES_PASSWORD`: Mot de passe de l'utilisateur (`babacar`)

### 2.2 Service `app` (Application Spring Boot pour AMD64)
- **Image Docker** : `gyumibear/m2sid-app:amd64`
- **Plateforme** : Linux/AMD64
- **Ports exposés** :
    - Port hôte `8080` ? Port conteneur `8080` (port standard Spring Boot)
- **Variables d'environnement** :
    - `SPRING_DATASOURCE_URL`: URL de connexion à la base de données (`jdbc:postgresql://db:5432/mydatabase`)
    - `SPRING_DATASOURCE_USERNAME`: Nom d'utilisateur pour la base de données (`babacar`)
    - `SPRING_DATASOURCE_PASSWORD`: Mot de passe pour la base de données (`babacar`)
- **Dépendances** :
    - Le service `app` dépend du service `db` pour s'assurer que la base de données est prête avant le démarrage de l'application.

### 2.3 Service `db-arm` (Base de données PostgreSQL pour ARM64)
- **Image Docker** : `gyumibear/m2sid-db:arm64`
- **Plateforme** : Linux/ARM64
- **Ports exposés** :
    - Port hôte `5433` ? Port conteneur `5432` (port alternatif pour éviter les conflits avec le service `db`)
- **Variables d'environnement** :
    - `POSTGRES_DB`: Nom de la base de données (`mydatabase`)
    - `POSTGRES_USER`: Utilisateur de la base de données (`babacar`)
    - `POSTGRES_PASSWORD`: Mot de passe de l'utilisateur (`babacar`)
- **Healthcheck** :
    - Commande : `pg_isready -U babacar` (vérifie si la base de données est opérationnelle)
    - Intervalle : 10 secondes
    - Timeout : 5 secondes
    - Retentatives : 5 fois

### 2.4 Service `app-arm` (Application Spring Boot pour ARM64)
- **Image Docker** : `gyumibear/m2sid-app:arm64`
- **Plateforme** : Linux/ARM64
- **Ports exposés** :
    - Port hôte `8081` ? Port conteneur `8080` (port alternatif pour éviter les conflits avec le service `app`)
- **Variables d'environnement** :
    - `SPRING_DATASOURCE_URL`: URL de connexion à la base de données (`jdbc:postgresql://db-arm:5432/mydatabase`)
    - `SPRING_DATASOURCE_USERNAME`: Nom d'utilisateur pour la base de données (`babacar`)
    - `SPRING_DATASOURCE_PASSWORD`: Mot de passe pour la base de données (`babacar`)
- **Dépendances** :
    - Le service `app-arm` dépend du service `db-arm` et attend que ce dernier soit en état "healthy" avant de démarrer.

---

## 3. Architecture Générale

L'architecture est conçue pour être multi-plateforme, prenant en charge les architectures AMD64 et ARM64.



### 3.1 Communication entre les services
- L'application Spring Boot (`app` ou `app-arm`) se connecte à sa base de données respective (`db` ou `db-arm`) via une URL JDBC spécifiée dans les variables d'environnement.
- Les ports exposés permettent d'accéder aux services depuis l'hôte :
  - Base de données AMD64 : `localhost:5432`
  - Base de données ARM64 : `localhost:5433`
  - Application AMD64 : `localhost:8080`
  - Application ARM64 : `localhost:8081`

---

## 4. Configuration des Variables d'Environnement

Les variables d'environnement suivantes sont utilisées pour configurer les services :

| Variable                          | Description                                   | Valeur par défaut       |
|-----------------------------------|-----------------------------------------------|--------------------------|
| `POSTGRES_DB`                     | Nom de la base de données                     | `mydatabase`            |
| `POSTGRES_USER`                   | Utilisateur de la base de données             | `babacar`               |
| `POSTGRES_PASSWORD`               | Mot de passe de l'utilisateur                 | `babacar`               |
| `SPRING_DATASOURCE_URL`           | URL de connexion à la base de données         | Définie selon le service|
| `SPRING_DATASOURCE_USERNAME`      | Nom d'utilisateur pour Spring Boot            | `babacar`               |
| `SPRING_DATASOURCE_PASSWORD`      | Mot de passe pour Spring Boot                 | `babacar`               |

---

## 5. Points Importants

- **Multi-plateforme** : Les images Docker sont spécifiques à chaque architecture (AMD64 et ARM64), garantissant ainsi la compatibilité avec différents environnements matériels.
- **Séparation des ports** : Les ports des services ARM64 (`db-arm` et `app-arm`) sont différents de ceux des services AMD64 (`db` et `app`) pour éviter les conflits.
- **Healthcheck** : Le service `db-arm` inclut un mécanisme de vérification de santé pour s'assurer que la base de données est opérationnelle avant le démarrage de l'application associée.

---

## 6. Conclusion

Cette architecture Docker offre une solution robuste et flexible pour déployer une application REST API Spring Boot avec une base de données PostgreSQL. Elle prend en charge plusieurs architectures matérielles tout en assurant une configuration modulaire et facilement extensible.