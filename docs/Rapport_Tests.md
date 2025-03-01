# Génération et Affichage du Rapport de Tests avec `surefire-report:report` dans un Projet Spring Boot

Le plugin **Maven Surefire** est utilisé pour exécuter les tests unitaires dans les projets Maven. Il génère également des rapports détaillés sur les résultats des tests sous forme de fichiers HTML. Voici comment configurer et utiliser le plugin `surefire-report:report` pour obtenir un rapport HTML des tests dans un projet Spring Boot.

---

## Étapes pour Générer le Rapport HTML

### 1. Ajouter la Dépendance et le Plugin dans `pom.xml`

Assurez-vous que votre fichier `pom.xml` contient les configurations nécessaires pour le plugin **Surefire**. Voici un exemple :

```xml
<build>
    <plugins>
        <!-- Plugin Maven Surefire -->
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-surefire-plugin</artifactId>
            <version>3.0.0</version> <!-- Utilisez la version appropriée -->
        </plugin>

        <!-- Plugin Surefire Report -->
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-surefire-report-plugin</artifactId>
            <version>3.0.0</version> <!-- Utilisez la version appropriée -->
        </plugin>
    </plugins>
</build>
```

---

### 2. Exécuter les Tests et Générer le Rapport

Pour générer le rapport HTML, suivez ces étapes :

#### a. Exécution des Tests
Exécutez d'abord vos tests avec la commande suivante :

```bash
mvn test
```

Cette commande exécute tous les tests unitaires dans votre projet et génère les résultats dans le répertoire `target/surefire-reports`.

#### b. Génération du Rapport HTML
Une fois les tests terminés, utilisez la commande suivante pour générer le rapport HTML :

```bash
mvn surefire-report:report
```

Cette commande transforme les résultats des tests en un rapport HTML lisible et le stocke dans le répertoire `target/site/surefire-report.html`.

---

### 3. Accéder au Rapport HTML

Après avoir exécuté la commande `mvn surefire-report:report`, ouvrez le fichier HTML généré dans votre navigateur :

1. Naviguez vers le répertoire `target/site/` de votre projet.
2. Ouvrez le fichier `surefire-report.html` dans votre navigateur web.

Par exemple, si votre projet est situé dans `/mon-projet/`, le chemin complet pourrait être :

```
/mon-projet/target/site/surefire-report.html
```

---

## Structure du Rapport HTML

Le rapport HTML fournit les informations suivantes :

- Liste des classes de test.
- Résultats détaillés pour chaque test (réussite, échec, ignoré).
- Détails des erreurs ou exceptions rencontrées lors des tests.
- Statistiques globales (nombre total de tests, réussites, échecs, etc.).

---

## Points Importants à Noter

- Assurez-vous que tous les tests sont configurés correctement et que les dépendances nécessaires sont présentes dans votre `pom.xml`.
- Si vous rencontrez des problèmes lors de la génération du rapport, vérifiez les versions des plugins et assurez-vous qu'ils sont compatibles avec votre version de Maven.
- Vous pouvez personnaliser le rapport en ajoutant des configurations supplémentaires dans le plugin `maven-surefire-report-plugin`.

---

En suivant ces étapes, vous devriez pouvoir générer et consulter facilement le rapport HTML des tests de votre API Spring Boot.