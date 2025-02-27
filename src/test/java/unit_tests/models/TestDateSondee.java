package unit_tests.models;

import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Choix;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.DateSondage;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.DateSondee;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Participant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Objects;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class TestDateSondee {

    private DateSondee dateSondee;

    @Mock
    private DateSondage dateSondageMock;

    @Mock
    private Participant participantMock;

    private final Long dateSondeeId = 1L;
    private final Choix choixValide = Choix.DISPONIBLE;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dateSondee = new DateSondee();
    }

    @Test
    @DisplayName("Test des getters/setters basiques")
    void testGettersSetters() {
        dateSondee.setDateSondeeId(dateSondeeId);
        dateSondee.setDateSondage(dateSondageMock);
        dateSondee.setParticipant(participantMock);
        dateSondee.setChoix(choixValide.name());

        assertAll("Vérification des propriétés basiques",
                () -> assertEquals(dateSondeeId, dateSondee.getDateSondeeId()),
                () -> assertSame(dateSondageMock, dateSondee.getDateSondage()),
                () -> assertSame(participantMock, dateSondee.getParticipant()),
                () -> assertEquals(choixValide.name(), dateSondee.getChoix())
        );
    }

    @ParameterizedTest
    @EnumSource(Choix.class)
    @DisplayName("Test de conversion valide pour le choix")
    void testChoixConversionValide(Choix choix) {
        dateSondee.setChoix(choix.name());
        assertEquals(choix.name(), dateSondee.getChoix());
    }

    @ParameterizedTest
    @ValueSource(strings = {"INVALIDE", "autre"})
    @DisplayName("Test de conversion invalide pour le choix")
    void testChoixConversionInvalide(String valeur) {
        assertThrows(IllegalArgumentException.class,
                () -> dateSondee.setChoix(valeur));
    }

    @Test
    @DisplayName("Test du constructeur paramétré")
    void testParameterizedConstructor() {
        DateSondee ds = new DateSondee(
                dateSondeeId,
                dateSondageMock,
                participantMock,
                choixValide
        );

        assertAll("Vérification du constructeur",
                () -> assertEquals(dateSondeeId, ds.getDateSondeeId()),
                () -> assertSame(dateSondageMock, ds.getDateSondage()),
                () -> assertSame(participantMock, ds.getParticipant()),
                () -> assertEquals(choixValide, Choix.valueOf(ds.getChoix()))
        );
    }

    @Test
    @DisplayName("Test de la relation bidirectionnelle avec DateSondage")
    void testBidirectionalRelationship() {
        when(dateSondageMock.getDateSondee()).thenReturn(List.of(dateSondee));

        dateSondee.setDateSondage(dateSondageMock);

        assertAll("Vérification de la relation",
                () -> assertSame(dateSondageMock, dateSondee.getDateSondage()),
                () -> assertTrue(dateSondageMock.getDateSondee().contains(dateSondee))
        );
    }

    @Test
    @DisplayName("Test de l'unicité participant-dateSondage")
    void testUniqueConstraint() {
        dateSondee.setDateSondage(dateSondageMock);
        dateSondee.setParticipant(participantMock);

        DateSondee ds2 = new DateSondee();
        ds2.setDateSondage(dateSondageMock);
        ds2.setParticipant(participantMock);

        assertTrue(Objects.equals(dateSondee.getDateSondage(), ds2.getDateSondage())
                && Objects.equals(dateSondee.getParticipant(), ds2.getParticipant()));
    }
}