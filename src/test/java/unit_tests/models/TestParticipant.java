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

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

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


}
