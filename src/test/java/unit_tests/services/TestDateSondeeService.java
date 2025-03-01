package unit_tests.services;

import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Sondage;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.repositories.DateSondeeRepository;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services.DateSondageService;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services.DateSondeeService;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services.ParticipantService;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services.SondageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class TestDateSondeeService {

    @Mock
    private DateSondeeRepository repository;

    @Mock
    private DateSondageService dateSondageService;

    @Mock
    private ParticipantService participantService;

    @Mock
    private SondageService sondageService;

    @InjectMocks
    private DateSondeeService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBestDate() {
        Long sondageId = 1L;
        Date date = new Date();
        List<Date> expectedDates = Collections.singletonList(date);

        // Mock SondageService.getById() pour retourner un sondage non clôturé
        Sondage sondage = new Sondage();
        sondage.setSondageId(sondageId);
        sondage.setCloture(false);
        when(sondageService.getById(sondageId)).thenReturn(sondage);

        // Mock repository.bestDate()
        when(repository.bestDate(sondageId)).thenReturn(expectedDates);

        List<Date> result = service.bestDate(sondageId); // Ligne 120 environ
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(date, result.get(0));

        verify(sondageService, times(1)).getById(sondageId);
        verify(repository, times(1)).bestDate(sondageId);
    }

    @Test
    void testMaybeBestDate() {
        Long sondageId = 1L;
        Date date = new Date();
        List<Date> expectedDates = Collections.singletonList(date);

        // Mock SondageService.getById() pour retourner un sondage non clôturé
        Sondage sondage = new Sondage();
        sondage.setSondageId(sondageId);
        sondage.setCloture(false);
        when(sondageService.getById(sondageId)).thenReturn(sondage);

        // Mock repository.maybeBestDate()
        when(repository.maybeBestDate(sondageId)).thenReturn(expectedDates);

        List<Date> result = service.maybeBestDate(sondageId); // Ligne 131 environ
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(date, result.get(0));

        verify(sondageService, times(1)).getById(sondageId);
        verify(repository, times(1)).maybeBestDate(sondageId);
    }
}