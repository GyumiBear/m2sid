package TestSondageService;

import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Participant;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Sondage;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.repositories.SondageRepository;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services.ParticipantService;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services.SondageService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TestSondageService {
    @Mock
    private SondageRepository repository;

    @Mock
    private ParticipantService participantService;

    @InjectMocks
    private SondageService sondageService;

    // Variables communes
    private Long sondageId;
    private String nom;
    private String description;
    private Date fin;
    private Boolean cloture;
    private Participant sampleParticipant;
    private Sondage sampleSondage;
    private Sondage updatedSondage;
    private List<Sondage> sondageList;

    @BeforeEach
    void initVariables() {
        // Initialisation des IDs et valeurs
        sondageId = 1L;
        nom = "Sondage Test";
        description = "Description Test";
        fin = new Date();
        cloture = false;

        // Création des entités
        sampleParticipant = new Participant();
        sampleParticipant.setParticipantId(1L);

        sampleSondage = new Sondage();
        sampleSondage.setSondageId(sondageId);
        sampleSondage.setNom(nom);
        sampleSondage.setCreateBy(sampleParticipant);

        updatedSondage = new Sondage();
        updatedSondage.setSondageId(sondageId);
        updatedSondage.setNom("Updated Name");

        // Listes mockées
        sondageList = Collections.singletonList(sampleSondage);
    }

}
