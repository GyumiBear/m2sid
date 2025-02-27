package unit_tests.models;

import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Commentaire;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.DateSondee;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Participant;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Sondage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

public class TestParticipant {
    private Participant participant;

    @Mock
    private Commentaire commentaireMock;

    @Mock
    private Sondage sondageMock;

    @Mock
    private DateSondee dateSondeeMock;

    private final Long participantId = 1L;
    private final String nom = "Dupont";
    private final String prenom = "Jean";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        participant = new Participant();
    }

    @Test
    @DisplayName("Test des getters/setters basiques")
    void testGettersSetters() {
        participant.setParticipantId(participantId);
        participant.setNom(nom);
        participant.setPrenom(prenom);

        assertAll("Vérification des propriétés basiques",
                () -> assertEquals(participantId, participant.getParticipantId()),
                () -> assertEquals(nom, participant.getNom()),
                () -> assertEquals(prenom, participant.getPrenom())
        );
    }

    @Test
    @DisplayName("Test du constructeur paramétré")
    void testParameterizedConstructor() {
        Participant p = new Participant(participantId, nom, prenom);

        assertAll("Vérification du constructeur",
                () -> assertEquals(participantId, p.getParticipantId()),
                () -> assertEquals(nom, p.getNom()),
                () -> assertEquals(prenom, p.getPrenom()),
                () -> assertTrue(p.getCommentaire().isEmpty()),
                () -> assertTrue(p.getSondages().isEmpty()),
                () -> assertTrue(p.getDateSondee().isEmpty())
        );
    }

    @Test
    @DisplayName("Test de la relation avec Sondage (createBy)")
    void testSondageRelationship() {
        participant.getSondages().add(sondageMock);

        assertAll("Vérification de la relation Sondage",
                () -> assertEquals(1, participant.getSondages().size()),
                () -> assertSame(sondageMock, participant.getSondages().get(0))
        );
    }

    @Test
    @DisplayName("Test de la relation avec DateSondee")
    void testDateSondeeRelationship() {
        participant.getDateSondee().add(dateSondeeMock);

        assertAll("Vérification de la relation DateSondee",
                () -> assertEquals(1, participant.getDateSondee().size()),
                () -> assertSame(dateSondeeMock, participant.getDateSondee().get(0))
        );
    }


}
