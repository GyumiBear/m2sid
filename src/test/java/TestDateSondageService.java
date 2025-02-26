import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.*;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.repositories.DateSondeeRepository;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services.DateSondageService;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services.DateSondeeService;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services.ParticipantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Date;
import java.util.List;

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

}
