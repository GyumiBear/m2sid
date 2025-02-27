package unit_tests.services;

import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.*;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.repositories.DateSondeeRepository;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services.DateSondageService;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services.DateSondeeService;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services.ParticipantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import util.TestUtil;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestDateSondageService {
    @Mock
    private DateSondeeRepository repository;

    @Mock
    private DateSondageService dateSondageService;

    @Mock
    private ParticipantService participantService;

    @InjectMocks
    private DateSondeeService dateSondeeService;

    // Variables communes
    private Long dateSondageId;
    private Long participantId;
    private Long dateSondeeId;
    private DateSondee dateSondeeToCreate;
    private DateSondage openDateSondage;
    private DateSondage closedDateSondage;
    private Participant participant;
    private Choix choix;
    private List<Date> expectedDates;

    @BeforeEach
    void initVariables() {
        // IDs
        dateSondageId = 1L;
        participantId = 2L;
        dateSondeeId = 3L;

        // Entités
        participant = new Participant();
        choix = Choix.DISPONIBLE;

        // Configuration DateSondage
        openDateSondage = new DateSondage();
        openDateSondage.setSondage(new Sondage());
        openDateSondage.getSondage().setCloture(false);

        closedDateSondage = new DateSondage();
        closedDateSondage.setSondage(new Sondage());
        closedDateSondage.getSondage().setCloture(true);

        // DateSondee
        dateSondeeToCreate = new DateSondee(dateSondeeId, openDateSondage, participant, choix);

        // Données mockées
        expectedDates = Collections.singletonList(new Date());
    }

    @Test
    @DisplayName("Création réussie quand le sondage est ouvert")
    void testCreate() {
        when(dateSondageService.getById(dateSondageId)).thenReturn(openDateSondage);
        when(participantService.getById(participantId)).thenReturn(participant);
        when(repository.save(any(DateSondee.class))).thenReturn(dateSondeeToCreate);

        DateSondee result = dateSondeeService.create(dateSondageId, participantId, new DateSondee());

        TestUtil.assertDto(dateSondeeToCreate, result);
        verifyInteractions();
    }

    @Test
    @DisplayName("Création avec paramètres et setters")
    void testCreateParams() {
        when(dateSondageService.getById(dateSondageId)).thenReturn(openDateSondage);
        when(participantService.getById(participantId)).thenReturn(participant);
        when(repository.save(dateSondeeToCreate)).thenReturn(dateSondeeToCreate);

        DateSondee result = dateSondeeService.create(dateSondageId, participantId, dateSondeeToCreate);

        TestUtil.assertDto(dateSondeeToCreate, result);
        verifyInteractions();
    }

    @Test
    @DisplayName("Échec création si sondage clos")
    void testCreateClotureDateSondage() {
        when(dateSondageService.getById(dateSondageId)).thenReturn(closedDateSondage);

        DateSondee result = dateSondeeService.create(dateSondageId, participantId, new DateSondee());

        assertNull(result, "Doit retourner null quand sondage clos");
        verify(dateSondageService).getById(dateSondageId);
        verifyNoOtherInteractions();
    }

    @Test
    @DisplayName("Récupération meilleures dates")
    void testBestDate() {
        when(repository.bestDate(dateSondageId)).thenReturn(expectedDates);

        List<Date> result = dateSondeeService.bestDate(dateSondageId);

        TestUtil.assertDto(expectedDates, result);
        verify(repository).bestDate(dateSondageId);
    }

    @Test
    @DisplayName("Récupération dates potentielles")
    void testMaybeBestDate() {
        when(repository.maybeBestDate(dateSondageId)).thenReturn(expectedDates);

        List<Date> result = dateSondeeService.maybeBestDate(dateSondageId);

        TestUtil.assertDto(expectedDates, result);
        verify(repository).maybeBestDate(dateSondageId);
    }

    // Méthodes helper
    private void verifyInteractions() {
        verify(dateSondageService).getById(dateSondageId);
        verify(participantService).getById(participantId);
        verify(repository).save(any(DateSondee.class));
    }

    private void verifyNoOtherInteractions() {
        verify(participantService, never()).getById(any());
        verify(repository, never()).save(any());
    }
}
