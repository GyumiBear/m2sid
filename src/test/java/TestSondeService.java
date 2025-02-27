import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.DateSondage;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.DateSondee;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Sondage;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.repositories.DateSondageRepository;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services.DateSondageService;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services.SondageService;
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
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestSondeService {

    @Mock
    private DateSondageRepository repository;

    @Mock
    private SondageService sondageService;

    @InjectMocks
    private DateSondageService dateSondageService;

    // Common variables
    private Long sondageId;
    private Long dateSondageId;
    private DateSondage sampleDateSondage;
    private DateSondage existingDateSondage;
    private DateSondage dateSondageToCreate;
    private DateSondage expectedDateSondage;
    private Sondage sondage;
    private Date date;
    private List<DateSondee> dateSondee;

    @BeforeEach
    void initVariables() {
        sondageId = 1L;
        dateSondageId = 1L;
        sampleDateSondage = new DateSondage();
        existingDateSondage = new DateSondage();
        dateSondageToCreate = new DateSondage();
        expectedDateSondage = new DateSondage();
        sondage = new Sondage();
        date = new Date();
        dateSondee = Collections.singletonList(new DateSondee());
    }

    @Test
    @DisplayName("Test getById method")
    void testGetById() {
        when(repository.getById(dateSondageId)).thenReturn(sampleDateSondage);

        DateSondage result = dateSondageService.getById(dateSondageId);

        TestUtil.assertDto(sampleDateSondage, result);
    }

    @Test
    @DisplayName("Test getBySondageId method")
    void testGetBySondageId() {
        when(repository.getAllBySondage(sondageId)).thenReturn(Collections.singletonList(sampleDateSondage));

        List<DateSondage> result = dateSondageService.getBySondageId(sondageId);

        assertEquals(1, result.size(), "Number of dateSondages should be 1");
        TestUtil.assertDto(sampleDateSondage, result.get(0));
    }

    @Test
    @DisplayName("Test create DateSondage")
    void testCreate() {
        when(sondageService.getById(sondageId)).thenReturn(sondage);
        when(repository.save(any(DateSondage.class))).thenReturn(expectedDateSondage);

        DateSondage result = dateSondageService.create(sondageId, dateSondageToCreate);

        TestUtil.assertDto(expectedDateSondage, result);
        verify(sondageService).getById(sondageId);
        verify(repository).save(dateSondageToCreate);
    }

    @Test
    @DisplayName("Test create DateSondage with parameters")
    void testCreateWithParameters() {
        Long specificDateSondageId = 2L;
        DateSondage dateSondageToCreate = new DateSondage(specificDateSondageId, date, sondage, dateSondee);
        DateSondage expected = new DateSondage();
        expected.setDateSondageId(specificDateSondageId);
        expected.setDate(date);
        expected.setSondage(sondage);
        expected.setDateSondee(dateSondee);

        when(sondageService.getById(sondageId)).thenReturn(sondage);
        when(repository.save(dateSondageToCreate)).thenReturn(expected);

        DateSondage result = dateSondageService.create(sondageId, dateSondageToCreate);

        TestUtil.assertDto(expected, result);
    }

    @Test
    @DisplayName("Test delete DateSondage")
    void testDelete() {
        existingDateSondage.setDateSondageId(dateSondageId);
        when(repository.findById(dateSondageId)).thenReturn(Optional.of(existingDateSondage));

        int result = dateSondageService.delete(dateSondageId);

        assertEquals(1, result, "DateSondage should be deleted");
        verify(repository).deleteById(dateSondageId);
    }

    @Test
    @DisplayName("Test getDate")
    void testGetDate() {
        existingDateSondage.setDate(date);
        assertEquals(date, existingDateSondage.getDate());
    }

    @Test
    @DisplayName("Test getDateSondee")
    void testGetDateSondee() {
        existingDateSondage.setDateSondee(dateSondee);
        assertEquals(dateSondee, existingDateSondage.getDateSondee());
    }

    @Test
    @DisplayName("Test delete Non-Existent DateSondage")
    void testDeleteNonExistent() {
        when(repository.findById(dateSondageId)).thenReturn(Optional.empty());

        int result = dateSondageService.delete(dateSondageId);

        assertEquals(0, result, "DateSondage should not be deleted");
        verify(repository, never()).deleteById(dateSondageId);
    }

}
