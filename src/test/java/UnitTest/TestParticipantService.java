package UnitTest;

import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Commentaire;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.DateSondee;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Participant;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Sondage;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.repositories.ParticipantRepository;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services.ParticipantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TestParticipantService {
    @Mock
    private ParticipantRepository repository;

    @InjectMocks
    private ParticipantService participantService;

    private Long participantId;
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

    @BeforeEach
    void initVariables() {
        // Initialisation des IDs et noms
        participantId = 1L;
        name = "John";
        lasteName = "Smith";

        // Création des entités à utiliser
        commentaire = new Commentaire();
        sondage = new Sondage();
        dateSondee = new DateSondee();

        // Configuration des participants à utiliser pour les tests
        SampleParticipant = new Participant();
        SampleParticipant.setParticipantId(participantId);
        SampleParticipant.setPrenom(name);
        SampleParticipant.setNom(lasteName);
        participantToCreate = new Participant();
        existingParticipant = new Participant(participantId, "Old", "Name");
        updatedParticipant = new Participant(participantId, name, lasteName);

        // Listes mockées de participants
        participantList = Collections.singletonList(SampleParticipant);
    }

}
