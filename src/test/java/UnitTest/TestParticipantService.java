package UnitTest;

import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Commentaire;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.DateSondee;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Participant;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Sondage;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.repositories.ParticipantRepository;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services.ParticipantService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TestParticipantService {
    @Mock
    private ParticipantRepository repository;

    @InjectMocks
    private ParticipantService participantService;

    private Long id;
    private String name;
    private String lasteName;
    private Participant SampleParticipant;
    private Participant participantToCreate;
    private Participant existingParticipant;
    private Participant updatedParticipant;
    private Commentaire commentaire;
    private Sondage sondage;
    private DateSondee dateSondee;
    private List<Participant> participantList;



}
