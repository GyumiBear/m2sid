# Document Fonctionnel : Architecture Docker pour une API REST Spring Boot

## 1. Introduction
Ce document d�crit l'architecture Docker utilis�e pour d�ployer une application REST API d�velopp�e avec Spring Boot.
L'application repose sur une base de donn�es PostgreSQL et prend en charge deux architectures mat�rielles : AMD64 (WINDOWS) et ARM64 (LINUX).


Le fichier `docker-compose.yml` d�finit quatre services principaux :
- **db** : Base de donn�es PostgreSQL pour les syst�mes AMD64.
- **app** : Application Spring Boot pour les syst�mes AMD64.
- **db-arm** : Base de donn�es PostgreSQL pour les syst�mes ARM64.
- **app-arm** : Application Spring Boot pour les syst�mes ARM64.

---

## 2. Description des Services

### 2.1 Service `db` (Base de donn�es PostgreSQL pour AMD64)
- **Image Docker** : `gyumibear/m2sid-db:amd64`
- **Plateforme** : Linux/AMD64
- **Ports expos�s** :
    - Port h�te `5432` ? Port conteneur `5432` (port standard PostgreSQL)
- **Variables d'environnement** :
    - `POSTGRES_DB`: Nom de la base de donn�es (`mydatabase`)
    - `POSTGRES_USER`: Utilisateur de la base de donn�es (`babacar`)
    - `POSTGRES_PASSWORD`: Mot de passe de l'utilisateur (`babacar`)

### 2.2 Service `app` (Application Spring Boot pour AMD64)
- **Image Docker** : `gyumibear/m2sid-app:amd64`
- **Plateforme** : Linux/AMD64
- **Ports expos�s** :
    - Port h�te `8080` ? Port conteneur `8080` (port standard Spring Boot)
- **Variables d'environnement** :
    - `SPRING_DATASOURCE_URL`: URL de connexion � la base de donn�es (`jdbc:postgresql://db:5432/mydatabase`)
    - `SPRING_DATASOURCE_USERNAME`: Nom d'utilisateur pour la base de donn�es (`babacar`)
    - `SPRING_DATASOURCE_PASSWORD`: Mot de passe pour la base de donn�es (`babacar`)
- **D�pendances** :
    - Le service `app` d�pend du service `db` pour s'assurer que la base de donn�es est pr�te avant le d�marrage de l'application.

### 2.3 Service `db-arm` (Base de donn�es PostgreSQL pour ARM64)
- **Image Docker** : `gyumibear/m2sid-db:arm64`
- **Plateforme** : Linux/ARM64
- **Ports expos�s** :
    - Port h�te `5433` ? Port conteneur `5432` (port alternatif pour �viter les conflits avec le service `db`)
- **Variables d'environnement** :
    - `POSTGRES_DB`: Nom de la base de donn�es (`mydatabase`)
    - `POSTGRES_USER`: Utilisateur de la base de donn�es (`babacar`)
    - `POSTGRES_PASSWORD`: Mot de passe de l'utilisateur (`babacar`)
- **Healthcheck** :
    - Commande : `pg_isready -U babacar` (v�rifie si la base de donn�es est op�rationnelle)
    - Intervalle : 10 secondes
    - Timeout : 5 secondes
    - Retentatives : 5 fois

### 2.4 Service `app-arm` (Application Spring Boot pour ARM64)
- **Image Docker** : `gyumibear/m2sid-app:arm64`
- **Plateforme** : Linux/ARM64
- **Ports expos�s** :
    - Port h�te `8081` ? Port conteneur `8080` (port alternatif pour �viter les conflits avec le service `app`)
- **Variables d'environnement** :
    - `SPRING_DATASOURCE_URL`: URL de connexion � la base de donn�es (`jdbc:postgresql://db-arm:5432/mydatabase`)
    - `SPRING_DATASOURCE_USERNAME`: Nom d'utilisateur pour la base de donn�es (`babacar`)
    - `SPRING_DATASOURCE_PASSWORD`: Mot de passe pour la base de donn�es (`babacar`)
- **D�pendances** :
    - Le service `app-arm` d�pend du service `db-arm` et attend que ce dernier soit en �tat "healthy" avant de d�marrer.

---

## 3. Architecture G�n�rale

L'architecture est con�ue pour �tre multi-plateforme, prenant en charge les architectures AMD64 et ARM64.



### 3.1 Communication entre les services
- L'application Spring Boot (`app` ou `app-arm`) se connecte � sa base de donn�es respective (`db` ou `db-arm`) via une URL JDBC sp�cifi�e dans les variables d'environnement.
- Les ports expos�s permettent d'acc�der aux services depuis l'h�te :
  - Base de donn�es AMD64 : `localhost:5432`
  - Base de donn�es ARM64 : `localhost:5433`
  - Application AMD64 : `localhost:8080`
  - Application ARM64 : `localhost:8081`

---

## 4. Configuration des Variables d'Environnement

Les variables d'environnement suivantes sont utilis�es pour configurer les services :

| Variable                          | Description                                   | Valeur par d�faut       |
|-----------------------------------|-----------------------------------------------|--------------------------|
| `POSTGRES_DB`                     | Nom de la base de donn�es                     | `mydatabase`            |
| `POSTGRES_USER`                   | Utilisateur de la base de donn�es             | `babacar`               |
| `POSTGRES_PASSWORD`               | Mot de passe de l'utilisateur                 | `babacar`               |
| `SPRING_DATASOURCE_URL`           | URL de connexion � la base de donn�es         | D�finie selon le service|
| `SPRING_DATASOURCE_USERNAME`      | Nom d'utilisateur pour Spring Boot            | `babacar`               |
| `SPRING_DATASOURCE_PASSWORD`      | Mot de passe pour Spring Boot                 | `babacar`               |

---

## 5. Points Importants

- **Multi-plateforme** : Les images Docker sont sp�cifiques � chaque architecture (AMD64 et ARM64), garantissant ainsi la compatibilit� avec diff�rents environnements mat�riels.
- **S�paration des ports** : Les ports des services ARM64 (`db-arm` et `app-arm`) sont diff�rents de ceux des services AMD64 (`db` et `app`) pour �viter les conflits.
- **Healthcheck** : Le service `db-arm` inclut un m�canisme de v�rification de sant� pour s'assurer que la base de donn�es est op�rationnelle avant le d�marrage de l'application associ�e.

---

## 6. Conclusion

Cette architecture Docker offre une solution robuste et flexible pour d�ployer une application REST API Spring Boot avec une base de donn�es PostgreSQL. Elle prend en charge plusieurs architectures mat�rielles tout en assurant une configuration modulaire et facilement extensible.