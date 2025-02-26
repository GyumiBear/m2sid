package TestSondageService;

import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Participant;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Sondage;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.repositories.SondageRepository;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services.ParticipantService;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services.SondageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import util.TestUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    @Test
    @DisplayName("Récupération par ID")
    void testGetById() {
        when(repository.getById(sondageId)).thenReturn(sampleSondage);

        Sondage result = sondageService.getById(sondageId);

        TestUtil.assertDto(sampleSondage, result);
        verify(repository).getById(sondageId);
    }

    @Test
    @DisplayName("Récupération de tous les sondages")
    void testGetAll() {
        when(repository.findAll()).thenReturn(sondageList);

        List<Sondage> result = sondageService.getAll();

        TestUtil.assertDtoListSize(sondageList, result);
        verify(repository).findAll();
    }

    @Test
    @DisplayName("Création basique")
    void testCreate() {
        when(participantService.getById(anyLong())).thenReturn(sampleParticipant);
        when(repository.save(sampleSondage)).thenReturn(sampleSondage);

        Sondage result = sondageService.create(1L, sampleSondage);

        TestUtil.assertDto(sampleSondage, result);
        verify(repository).save(sampleSondage);
    }

    @Test
    @DisplayName("Création avec paramètres complets")
    void testCreateWithParameters() {
        Sondage fullSondage = new Sondage(sondageId, nom, description, fin, cloture,
                new ArrayList<>(), new ArrayList<>(), sampleParticipant);

        when(participantService.getById(anyLong())).thenReturn(sampleParticipant);
        when(repository.save(fullSondage)).thenReturn(fullSondage);

        Sondage result = sondageService.create(1L, fullSondage);

        TestUtil.assertDto(fullSondage, result);
        assertEquals(sampleParticipant, result.getCreateBy());
    }

}
