# G�n�ration et Affichage du Rapport de Tests avec `surefire-report:report` dans un Projet Spring Boot

Le plugin **Maven Surefire** est utilis� pour ex�cuter les tests unitaires dans les projets Maven. Il g�n�re �galement des rapports d�taill�s sur les r�sultats des tests sous forme de fichiers HTML. Voici comment configurer et utiliser le plugin `surefire-report:report` pour obtenir un rapport HTML des tests dans un projet Spring Boot.

---

## �tapes pour G�n�rer le Rapport HTML

### 1. Ajouter la D�pendance et le Plugin dans `pom.xml`

Assurez-vous que votre fichier `pom.xml` contient les configurations n�cessaires pour le plugin **Surefire**. Voici un exemple :

```xml
<build>
    <plugins>
        <!-- Plugin Maven Surefire -->
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-surefire-plugin</artifactId>
            <version>3.0.0</version> <!-- Utilisez la version appropri�e -->
        </plugin>

        <!-- Plugin Surefire Report -->
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-surefire-report-plugin</artifactId>
            <version>3.0.0</version> <!-- Utilisez la version appropri�e -->
        </plugin>
    </plugins>
</build>
```

---

### 2. Ex�cuter les Tests et G�n�rer le Rapport

Pour g�n�rer le rapport HTML, suivez ces �tapes :

#### a. Ex�cution des Tests
Ex�cutez d'abord vos tests avec la commande suivante :

```bash
mvn test
```

Cette commande ex�cute tous les tests unitaires dans votre projet et g�n�re les r�sultats dans le r�pertoire `target/surefire-reports`.

#### b. G�n�ration du Rapport HTML
Une fois les tests termin�s, utilisez la commande suivante pour g�n�rer le rapport HTML :

```bash
mvn surefire-report:report
```

Cette commande transforme les r�sultats des tests en un rapport HTML lisible et le stocke dans le r�pertoire `target/site/surefire-report.html`.

---

### 3. Acc�der au Rapport HTML

Apr�s avoir ex�cut� la commande `mvn surefire-report:report`, ouvrez le fichier HTML g�n�r� dans votre navigateur :

1. Naviguez vers le r�pertoire `target/site/` de votre projet.
2. Ouvrez le fichier `surefire-report.html` dans votre navigateur web.

Par exemple, si votre projet est situ� dans `/mon-projet/`, le chemin complet pourrait �tre :

```
/mon-projet/target/site/surefire-report.html
```

---

## Structure du Rapport HTML

Le rapport HTML fournit les informations suivantes :

- Liste des classes de test.
- R�sultats d�taill�s pour chaque test (r�ussite, �chec, ignor�).
- D�tails des erreurs ou exceptions rencontr�es lors des tests.
- Statistiques globales (nombre total de tests, r�ussites, �checs, etc.).

---

## Points Importants � Noter

- Assurez-vous que tous les tests sont configur�s correctement et que les d�pendances n�cessaires sont pr�sentes dans votre `pom.xml`.
- Si vous rencontrez des probl�mes lors de la g�n�ration du rapport, v�rifiez les versions des plugins et assurez-vous qu'ils sont compatibles avec votre version de Maven.
- Vous pouvez personnaliser le rapport en ajoutant des configurations suppl�mentaires dans le plugin `maven-surefire-report-plugin`.

---

En suivant ces �tapes, vous devriez pouvoir g�n�rer et consulter facilement le rapport HTML des tests de votre API Spring Boot.