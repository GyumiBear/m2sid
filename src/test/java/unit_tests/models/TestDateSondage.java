package unit_tests.models;

import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.DateSondage;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.DateSondee;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Sondage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class TestDateSondage {

    private DateSondage dateSondage;

    @Mock
    private Sondage sondageMock;

    @Mock
    private DateSondee dateSondeeMock;

    private final Long dateSondageId = 1L;
    private final Date date = new Date();
    private final List<DateSondee> dateSondeeList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dateSondage = new DateSondage();
        dateSondeeList.add(dateSondeeMock);
    }

    @Test
    @DisplayName("Test des getters/setters basiques")
    void testGettersSetters() {
        dateSondage.setDateSondageId(dateSondageId);
        dateSondage.setDate(date);
        dateSondage.setSondage(sondageMock);
        dateSondage.setDateSondee(dateSondeeList);

        assertAll("Vérification des propriétés basiques",
                () -> assertEquals(dateSondageId, dateSondage.getDateSondageId()),
                () -> assertEquals(date, dateSondage.getDate()),
                () -> assertSame(sondageMock, dateSondage.getSondage()),
                () -> assertEquals(1, dateSondage.getDateSondee().size()),
                () -> assertSame(dateSondeeMock, dateSondage.getDateSondee().get(0))
        );
    }

    @Test
    @DisplayName("Test du constructeur paramétré")
    void testParameterizedConstructor() {
        DateSondage ds = new DateSondage(
                dateSondageId,
                date,
                sondageMock,
                dateSondeeList
        );

        assertAll("Vérification du constructeur",
                () -> assertEquals(dateSondageId, ds.getDateSondageId()),
                () -> assertEquals(date, ds.getDate()),
                () -> assertSame(sondageMock, ds.getSondage()),
                () -> assertEquals(1, ds.getDateSondee().size())
        );
    }

    @Test
    @DisplayName("Test de la relation bidirectionnelle avec DateSondee")
    void testBidirectionalRelationship() {
        when(dateSondeeMock.getDateSondage()).thenReturn(dateSondage);

        dateSondage.getDateSondee().add(dateSondeeMock);

        assertAll("Vérification de la relation",
                () -> assertTrue(dateSondage.getDateSondee().contains(dateSondeeMock)),
                () -> assertEquals(dateSondage, dateSondeeMock.getDateSondage())
        );
    }

    @Test
    @DisplayName("Test de l'initialisation par défaut")
    void testDefaultInitialization() {
        assertAll("Vérification des valeurs par défaut",
                () -> assertNull(dateSondage.getDateSondageId()),
                () -> assertNotNull(dateSondage.getSondage()),
                () -> assertNotNull(dateSondage.getDateSondee()),
                () -> assertTrue(dateSondage.getDateSondee().isEmpty())
        );
    }
}