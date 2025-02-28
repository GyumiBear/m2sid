package unit_tests.sondage;

import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Commentaire;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.DateSondage;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Participant;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Sondage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

class TestSondage {

    private Sondage sondage;

    @Mock
    private Commentaire commentaireMock;

    @Mock
    private DateSondage dateSondageMock;

    @Mock
    private Participant participantMock;

    private final Long sondageId = 1L;
    private final String nom = "Sondage vacances";
    private final Date fin = new Date();
    private final Boolean cloture = false;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sondage = new Sondage();
    }

    @Test
    @DisplayName("Test des getters/setters basiques")
    void testGettersSetters() {
        sondage.setSondageId(sondageId);
        sondage.setNom(nom);
        sondage.setFin(fin);
        sondage.setCloture(cloture);
        sondage.setCreateBy(participantMock);

        assertAll("Vérification des propriétés basiques",
                () -> assertEquals(sondageId, sondage.getSondageId()),
                () -> assertEquals(nom, sondage.getNom()),
                () -> assertEquals(fin, sondage.getFin()),
                () -> assertEquals(cloture, sondage.getCloture()),
                () -> assertSame(participantMock, sondage.getCreateBy())
        );
    }

    @Test
    @DisplayName("Test du constructeur paramétré")
    void testParameterizedConstructor() {
        List<Commentaire> commentaires = List.of(commentaireMock);
        List<DateSondage> dates = List.of(dateSondageMock);

        Sondage s = new Sondage(
                sondageId,
                nom,
                "Description test",
                fin,
                cloture,
                commentaires,
                dates,
                participantMock
        );

        assertAll("Vérification du constructeur",
                () -> assertEquals(sondageId, s.getSondageId()),
                () -> assertEquals(nom, s.getNom()),
                () -> assertEquals(1, s.getCommentaires().size()),
                () -> assertEquals(1, s.getDateSondage().size()),
                () -> assertSame(participantMock, s.getCreateBy())
        );
    }
}