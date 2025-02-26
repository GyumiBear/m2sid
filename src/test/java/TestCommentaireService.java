import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Commentaire;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Participant;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Sondage;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.repositories.CommentaireRepository;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services.CommentaireService;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services.ParticipantService;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services.SondageService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class TestCommentaireService {
    @Mock
    private CommentaireRepository commentaireRepository;

    @Mock
    private SondageService sondageService;

    @Mock
    private ParticipantService participantService;

    @InjectMocks
    private CommentaireService commentaireService;

    // Variables partagées par tous les tests
    private static List<Commentaire> mockCommentaireList;
    private static Commentaire newCommentaire;
    private static Commentaire expectedCommentaire;

    @BeforeAll
    static void setUpAll() {
        // Initialisation des commentaires simulés
        mockCommentaireList = List.of(new Commentaire(), new Commentaire());
        IntStream.range(0, mockCommentaireList.size())
                .forEach(index -> {
                    mockCommentaireList.get(index).setCommentaireId((long) (index + 1));
                    mockCommentaireList.get(index).setCommentaire("commentaire" + (index + 1L));
                });

        // Initialisation d'un nouveau commentaire
        newCommentaire = new Commentaire();
        newCommentaire.setCommentaire("Nouveau commentaire");

        // Initialisation du commentaire attendu
        expectedCommentaire = new Commentaire();
        expectedCommentaire.setCommentaireId(1L);
        expectedCommentaire.setCommentaire("Nouveau commentaire");
    }

    @BeforeEach
    void setUp() {
        // Configuration des comportements simulés pour chaque test
        lenient().when(commentaireRepository.getAllBySondage(1L)).thenReturn(mockCommentaireList); // Simule getAllBySondage pour un sondage avec ID 1
        lenient().when(commentaireRepository.findById(1L)).thenReturn(Optional.of(mockCommentaireList.get(0))); // Simule findById pour un commentaire existant
        lenient().when(commentaireRepository.findById(3L)).thenReturn(Optional.empty()); // Simule findById pour un commentaire inexistant

        // Simule la récupération d'un sondage et d'un participant
        lenient().when(sondageService.getById(1L)).thenReturn(new Sondage());
        lenient().when(participantService.getById(2L)).thenReturn(new Participant());

        // Simule la sauvegarde d'un commentaire
        lenient().when(commentaireRepository.save(any(Commentaire.class))).thenAnswer(invocation -> {
            Commentaire savedCommentaire = invocation.getArgument(0);
            savedCommentaire.setCommentaireId(expectedCommentaire.getCommentaireId());
            return savedCommentaire;
        });
    }

    @Test
    @DisplayName("getBySondageId() doit renvoyer 2 lorsqu'il y a deux commentaires dans le service")
    void testGetBySondageIdWhenTwoCommentsThenResultSizeShouldBeTwo() {
        // Vérifie que la méthode getBySondageId retourne la liste correcte
        assertEquals(2, commentaireService.getBySondageId(1L).size(), "Le nombre de commentaires attendu est 2");
    }

    @Test
    @DisplayName("getBySondageId() doit renvoyer 0 lorsqu'il n'y a aucun commentaire dans le service")
    void testGetBySondageIdWhenNoCommentsThenResultSizeShouldBeZero() {
        // Simule un sondage sans commentaires
        when(commentaireRepository.getAllBySondage(3L)).thenReturn(Collections.emptyList());

        // Vérifie que la méthode getBySondageId retourne une liste vide
        assertEquals(0, commentaireService.getBySondageId(3L).size(), "Le nombre de commentaires attendu est 0");
    }

    @Test
    @DisplayName("addCommantaire() doit ajouter un nouveau commentaire correctement")
    void testAddCommentaire() {
        // Appel de la méthode à tester
        Commentaire result = commentaireService.addCommantaire(1L, 2L, newCommentaire);

        // Vérifie que le résultat correspond au commentaire attendu
        assertNotNull(result);
        assertEquals(expectedCommentaire.getCommentaireId(), result.getCommentaireId());
        assertEquals(expectedCommentaire.getCommentaire(), result.getCommentaire());

        // Vérifie les interactions avec les mocks
        verify(sondageService, times(1)).getById(1L);
        verify(participantService, times(1)).getById(2L);
        verify(commentaireRepository, times(1)).save(any(Commentaire.class));
    }

    @Test
    @DisplayName("update() doit mettre à jour un commentaire existant")
    void testUpdateExistingCommentaire() {
        // Appel de la méthode à tester
        Commentaire updatedCommentaire = new Commentaire();
        updatedCommentaire.setCommentaireId(1L);
        updatedCommentaire.setCommentaire("Commentaire mis à jour");

        Commentaire result = commentaireService.update(1L, updatedCommentaire);

        // Vérifie que le résultat correspond au commentaire mis à jour
        assertNotNull(result);
        assertEquals("Commentaire mis à jour", result.getCommentaire());

        // Vérifie les interactions avec les mocks
        verify(commentaireRepository, times(1)).save(any(Commentaire.class));
    }

}
