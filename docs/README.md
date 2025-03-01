
# Configuration et Lancement du Projet MySurvey REST API

Bienvenue dans le projet **MySurvey** ! Ce guide explique comment configurer, construire et ex�cuter votre projet Spring Boot sur votre machine locale. Il inclut �galement quelques conseils pratiques sur l'utilisation de Markdown pour r�diger des documents clairs et bien structur�s.

---

## Table des mati�res

1. [Introduction](#introduction)
2. [Pr�requis](#pr�requis)
3. [�tapes de lancement](#�tapes-de-lancement)
4. [Acc�s � l'application](#acc�s-�-lapplication)
5. [Notes suppl�mentaires](#notes-suppl�mentaires)
6. [Documentation Docker (Optionnel)](#documentation-docker-optionnel)
7. [Ressources Utiles](#ressources-utiles)

---

## Introduction

Ce guide explique comment configurer, construire et ex�cuter votre projet Spring Boot sur votre machine locale. Il fournit �galement des instructions d�taill�es pour utiliser Docker si vous choisissez cette option.

---

## Pr�requis

Avant de commencer, assurez-vous d'avoir les outils suivants install�s :

### 1. Java
V�rifiez que Java est install� sur votre syst�me :
```bash
java -version
```

### 2. Maven
Assurez-vous que l'ex�cutable de Maven fonctionne correctement. Si vous utilisez le wrapper Maven fourni avec le projet, v�rifiez sa version :
```bash
./mvnw -v
```

### 3. Docker (facultatif)
Si vous pr�voyez d'utiliser Docker pour ex�cuter l'application, installez Docker et v�rifiez qu'il fonctionne :
```bash
docker --version
docker-compose --version
```

---

## �tapes de lancement

### 1. Cloner ou t�l�charger le projet

Si vous n'avez pas encore le code source du projet sur votre machine, clonez-le depuis le d�p�t Git ou t�l�chargez-le au format ZIP.

#### Via Git :
```bash
git clone https://github.com/GyumiBear/m2sid.git
cd m2sid
```

#### Via ZIP :
T�l�chargez et extrayez l'archive depuis : [Lien vers le d�p�t GitHub](https://github.com/GyumiBear/m2sid).

### 2. Construire le projet

Utilisez Maven pour construire le projet et installer toutes les d�pendances n�cessaires :
```bash
mvn clean install
```

### 3. Lancer l'application

Pour d�marrer l'application Spring Boot directement � partir des sources, utilisez la commande suivante :
```bash
docker-compose up
```

Cela lancera l'application en chargeant le profil par d�faut et toute configuration externe sp�cifi�e.

---

## Acc�s � l'application

Une fois l'application d�marr�e, elle sera accessible via votre navigateur web ou un outil comme Postman � l'adresse suivante :

```
http://localhost:8080
```

---

## Notes suppl�mentaires

- **Docker** : Si vous travaillez avec Docker, assurez-vous que Docker est bien install� et fonctionnel sur votre machine. Vous pouvez ensuite utiliser `docker-compose` pour d�marrer les services associ�s.
- **Logs** : En cas de probl�me, consultez les logs g�n�r�s lors du d�marrage pour plus d'informations. Ils peuvent �tre affich�s en temps r�el avec la commande suivante :
  ```bash
  docker-compose logs -f
  ```

---

## Documentation Docker (Optionnel)

Si vous choisissez d'utiliser Docker pour ex�cuter l'application, voici les �tapes � suivre :

### 1. Construction des Images Docker

Construisez les images Docker pour l'application et la base de donn�es :
```bash
docker-compose build
```

### 2. Lancement des Conteneurs

D�marrez les conteneurs avec la commande suivante :
```bash
docker-compose up
```

Les services suivants seront lanc�s :
- **Application Spring Boot** : Accessible � `http://localhost:8080`
- **Base de donn�es PostgreSQL** : Accessible � `jdbc:postgresql://localhost:5432/mysurvey`

### 3. Arr�t des Conteneurs

Pour arr�ter les conteneurs, utilisez la commande :
```bash
docker-compose down
```

---

## Ressources Utiles

- **Documentation Docker** : [https://hub.docker.com/repository/docker/gyumibear/m2sid-app/general](https://hub.docker.com/repository/docker/gyumibear/m2sid-app/general)
- **Base de Donn�es Docker** : [https://hub.docker.com/repository/docker/gyumibear/m2sid-db/general](https://hub.docker.com/repository/docker/gyumibear/m2sid-db/general)

---

## Support et Contribuer

Si vous rencontrez des probl�mes ou souhaitez contribuer au projet, n'h�sitez pas � ouvrir une issue ou une pull request sur notre d�p�t GitHub.

Merci d'utiliser **MySurvey** !
