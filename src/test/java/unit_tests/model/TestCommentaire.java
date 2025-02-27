package unit_tests.model;

import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Commentaire;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Participant;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Sondage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class TestCommentaire {

    private final Long commentaireId = 1L;
    private final String texte = "Excellent sondage !";
    private final Sondage sondage = mock(Sondage.class);
    private final Participant participant = mock(Participant.class);

    @Test
    @DisplayName("Test des getters/setters basiques")
    void testGettersSetters() {
        Commentaire commentaire = new Commentaire();

        commentaire.setCommentaireId(commentaireId);
        commentaire.setCommentaire(texte);
        commentaire.setSondage(sondage);
        commentaire.setParticipant(participant);

        assertAll("Vérification des propriétés basiques",
                () -> assertEquals(commentaireId, commentaire.getCommentaireId()),
                () -> assertEquals(texte, commentaire.getCommentaire()),
                () -> assertSame(sondage, commentaire.getSondage()),
                () -> assertSame(participant, commentaire.getParticipant())
        );
    }

    @Test
    @DisplayName("Test des relations JPA")
    void testJpaRelationships() {
        Commentaire commentaire = new Commentaire();

        // Création de mocks pour les dépendances
        Sondage sondageMock = mock(Sondage.class);
        Participant participantMock = mock(Participant.class);

        commentaire.setSondage(sondageMock);
        commentaire.setParticipant(participantMock);

        assertAll("Vérification des relations",
                () -> assertNotNull(commentaire.getSondage()),
                () -> assertNotNull(commentaire.getParticipant()),
                () -> assertSame(sondageMock, commentaire.getSondage()),
                () -> assertSame(participantMock, commentaire.getParticipant())
        );
    }

    @Test
    @DisplayName("Test du constructeur par défaut")
    void testDefaultConstructor() {
        Commentaire commentaire = new Commentaire();

        assertAll("Vérification des valeurs par défaut",
                () -> assertNull(commentaire.getCommentaireId()),
                () -> assertNull(commentaire.getCommentaire()),
                () -> assertNotNull(commentaire.getSondage()), // Si initialisé dans le constructeur
                () -> assertNotNull(commentaire.getParticipant()) // Si initialisé dans le constructeur
        );
    }
}
