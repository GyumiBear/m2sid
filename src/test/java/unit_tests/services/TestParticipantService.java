package unit_tests.services;

import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Commentaire;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.DateSondee;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Participant;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Sondage;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.repositories.ParticipantRepository;
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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

    @Test
    @DisplayName("récupére par ID")
    void testGetById() {
        when(repository.getById(participantId)).thenReturn(SampleParticipant); // Mock de getById()
        Participant result = participantService.getById(participantId);
        assertNotNull(result, "Le participant devrait être récupéré");
        TestUtil.assertDto(SampleParticipant, result);
        verify(repository).getById(participantId); // Vérification de l'appel à getById()
    }

    @Test
    @DisplayName("Récupération de tous les participants")
    void testGetAll() {
        when(repository.findAll()).thenReturn(participantList);
        List<Participant> result = participantService.getAll();
        TestUtil.assertDtoListSize(participantList, result);
        verify(repository).findAll();
    }

    @Test
    @DisplayName("Création basique")
    void testCreate() {
        when(repository.save(participantToCreate)).thenReturn(SampleParticipant);
        Participant result = participantService.create(participantToCreate);
        TestUtil.assertDto(SampleParticipant, result);
        verify(repository).save(participantToCreate);
    }

    @Test
    @DisplayName("Création avec paramètres")
    void testCreateWithParameters() {
        Participant paramParticipant = new Participant(participantId, name, lasteName);
        when(repository.save(paramParticipant)).thenReturn(paramParticipant);
        Participant result = participantService.create(paramParticipant);
        TestUtil.assertDto(paramParticipant, result);
        verify(repository).save(paramParticipant);
    }

    @Test
    @DisplayName("Mise à jour réussie")
    void testUpdate() {
        when(repository.findById(participantId)).thenReturn(Optional.of(existingParticipant));
        when(repository.save(updatedParticipant)).thenReturn(updatedParticipant);

        Participant result = participantService.update(participantId, updatedParticipant);

        TestUtil.assertDto(updatedParticipant, result);
    }

    @Test
    @DisplayName("Suppression réussie")
    void testDelete() {
        when(repository.findById(participantId)).thenReturn(Optional.of(existingParticipant));

        int result = participantService.delete(participantId);

        assertEquals(1, result, "La suppression devrait retourner 1");
    }

    @Test
    @DisplayName("Échec mise à jour participant inexistant")
    void testUpdateNonExistent() {
        when(repository.findById(participantId)).thenReturn(Optional.empty());

        Participant result = participantService.update(participantId, updatedParticipant);

        assertNull(result);
        verify(repository).findById(participantId);
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Échec suppression participant inexistant")
    void testDeleteNonExistent() {
        when(repository.findById(participantId)).thenReturn(Optional.empty());

        int result = participantService.delete(participantId);

        assertEquals(0, result);
        verify(repository).findById(participantId);
        verify(repository, never()).deleteById(any());
    }

    // Tests des getters/setters
    @Test
    @DisplayName("Vérification getter/setter commentaires")
    void testCommentaireHandling() {
        SampleParticipant.setCommentaire(List.of(commentaire));
        List<Commentaire> result = SampleParticipant.getCommentaire();

        assertEquals(1, result.size());
        TestUtil.assertDto(commentaire, result.get(0));
    }

    @Test
    @DisplayName("Vérification getter/setter sondages")
    void testSondageHandling() {
        SampleParticipant.setSondages(List.of(sondage));
        List<Sondage> result = SampleParticipant.getSondages();

        assertEquals(1, result.size());
        TestUtil.assertDto(sondage, result.get(0));
    }

    @Test
    @DisplayName("Vérification getter/setter dates sondees")
    void testDateSondeeHandling() {
        SampleParticipant.setDateSondee(List.of(dateSondee));
        List<DateSondee> result = SampleParticipant.getDateSondee();

        assertEquals(1, result.size());
        TestUtil.assertDto(dateSondee, result.get(0));
    }

    @Test
    @DisplayName("Vérification égalité complète")
    void testFullEquality() {
        Participant p1 = createFullParticipant();
        Participant p2 = createFullParticipant();

        TestUtil.assertDto(p1, p2);
    }

    @Test
    @DisplayName("Vérification inégalité")
    void testInequality() {
        Participant base = createFullParticipant();

        // Test différence ID
        Participant diffId = createFullParticipant();
        diffId.setParticipantId(2L);
        assertNotEquals(base, diffId);

        // Test différence prénom
        Participant diffPrenom = createFullParticipant();
        diffPrenom.setPrenom("Jane");
        assertNotEquals(base, diffPrenom);

        // Test différence nom
        Participant diffNom = createFullParticipant();
        diffNom.setNom("Doe");
        assertNotEquals(base, diffNom);
    }

    @Test
    @DisplayName("Vérification hashcode cohérent")
    void testHashCodeConsistency() {
        Participant p1 = createFullParticipant();
        Participant p2 = createFullParticipant();

        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    @DisplayName("Vérification format toString")
    void testStringRepresentation() {
        String expected = String.format(
                "Participant{participantId=%d, nom='%s', prenom='%s'}",
                participantId, lasteName, name // Inverser ici
        );

        assertEquals(expected, SampleParticipant.toString());
    }
    // Méthodes helper
    private Participant createFullParticipant() {
        Participant p = new Participant(participantId, name, lasteName);
        p.setCommentaire(List.of(commentaire));
        p.setSondages(List.of(sondage));
        p.setDateSondee(List.of(dateSondee));
        return p;
    }

    private void verifyInteractions() {
        verify(repository).findById(participantId);
        verify(repository).save(any());
        verify(repository).deleteById(participantId);
    }

}
