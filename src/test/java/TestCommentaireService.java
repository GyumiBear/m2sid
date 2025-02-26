import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Commentaire;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.repositories.CommentaireRepository;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services.CommentaireService;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services.ParticipantService;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services.SondageService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.IntStream;

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

}
