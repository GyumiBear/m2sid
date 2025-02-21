# Étape de construction avec Maven
FROM maven:3.8.1-jdk-11-slim AS build

# Définir le répertoire de travail dans le conteneur
WORKDIR /app

ENV SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/mydatabase
ENV SPRING_DATASOURCE_USERNAME=myuser
ENV SPRING_DATASOURCE_PASSWORD=mypassword
ENV SPRING_JPA_HIBERNATE_DDL_AUTO=update

# Copier les fichiers locaux dans le conteneur
COPY . .

# Construire le projet
RUN mvn clean package -DskipTests

# Étape finale avec une image runtime légère
FROM openjdk:17-jdk-slim

# Définir le répertoire de travail dans le conteneur
WORKDIR /app

# Copier le fichier JAR depuis l'étape de construction
COPY --from=build /app/target/MySurvey-0.0.1-SNAPSHOT.jar app.jar

# Rend le port 8080 disponible pour la connection des clients
EXPOSE 8080

# Lance l'application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]