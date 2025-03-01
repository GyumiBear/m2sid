package E2E;

import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.MySurveyApplication;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.dtos.CommentaireDto;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.dtos.DateSondageDto;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.dtos.DateSondeeDto;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.dtos.ParticipantDto;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.dtos.SondageDto;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Choix;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        classes = MySurveyApplication.class
)
@ActiveProfiles("test")
public class TestSondageEndToEnd {

    private static final String SONDAGE_BASE_PATH = "/api/sondage/";
    private static final String PARTICIPANT_BASE_PATH = "/api/participant/";
    private static final String DATE_SONDAGE_BASE_PATH = "/api/date/";

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8081; // Aligné avec vos tests précédents
    }

    @Test
    void sondageTestEndToEnd() throws Exception {
        Response res;

        // Création d’un participant (créateur du sondage)
        ParticipantDto participantDto1 = new ParticipantDto();
        participantDto1.setNom("Dupont");
        participantDto1.setPrenom("Jean");
        res = given()
                .contentType(ContentType.JSON)
                .body(participantDto1)
                .when()
                .post(PARTICIPANT_BASE_PATH)
                .then()
                .statusCode(201)
                .extract()
                .response();
        ParticipantDto createdParticipant1 = res.as(ParticipantDto.class);
        Long participant1Id = createdParticipant1.getParticipantId();

        // Création d’un second participant (pour participations et commentaires)
        ParticipantDto participantDto2 = new ParticipantDto();
        participantDto2.setNom("Martin");
        participantDto2.setPrenom("Sophie");
        res = given()
                .contentType(ContentType.JSON)
                .body(participantDto2)
                .when()
                .post(PARTICIPANT_BASE_PATH)
                .then()
                .statusCode(201)
                .extract()
                .response();
        ParticipantDto createdParticipant2 = res.as(ParticipantDto.class);
        Long participant2Id = createdParticipant2.getParticipantId();

        // Récupération de tous les sondages (liste initiale)
        res = given()
                .contentType(ContentType.JSON)
                .when()
                .get(SONDAGE_BASE_PATH)
                .then()
                .statusCode(200)
                .extract()
                .response();
        List<SondageDto> sondages = res.jsonPath().getList(".", SondageDto.class);
        int initialSize = sondages.size();

        // Création d’un sondage
        SondageDto sondageDto = new SondageDto();
        sondageDto.setCreateBy(participant1Id);
        sondageDto.setNom("Sondage Test");
        sondageDto.setCloture(false);
        sondageDto.setFin(new Date());
        sondageDto.setDescription("Description initiale");
        res = given()
                .contentType(ContentType.JSON)
                .body(sondageDto)
                .when()
                .post(SONDAGE_BASE_PATH)
                .then()
                .statusCode(201)
                .extract()
                .response();
        SondageDto createdSondage = res.as(SondageDto.class);
        Long sondageId = createdSondage.getSondageId();
        assertEquals(sondageDto.getCreateBy(), createdSondage.getCreateBy());
        assertEquals(sondageDto.getNom(), createdSondage.getNom());
        assertEquals(sondageDto.getCloture(), createdSondage.getCloture());
        assertEquals(sondageDto.getDescription(), createdSondage.getDescription());

        // Récupération du sondage créé
        res = given()
                .contentType(ContentType.JSON)
                .when()
                .get(SONDAGE_BASE_PATH + sondageId)
                .then()
                .statusCode(200)
                .extract()
                .response();
        SondageDto retrievedSondage = res.as(SondageDto.class);
        assertEquals(createdSondage.getSondageId(), retrievedSondage.getSondageId());
        assertEquals(createdSondage.getNom(), retrievedSondage.getNom());
        assertEquals(createdSondage.getDescription(), retrievedSondage.getDescription());

        // Mise à jour du sondage
        sondageDto.setNom("Sondage Modifié");
        sondageDto.setDescription("Description modifiée");
        res = given()
                .contentType(ContentType.JSON)
                .body(sondageDto)
                .when()
                .put(SONDAGE_BASE_PATH + sondageId)
                .then()
                .statusCode(200)
                .extract()
                .response();
        SondageDto updatedSondage = res.as(SondageDto.class);
        assertEquals(sondageDto.getNom(), updatedSondage.getNom());
        assertEquals(sondageDto.getDescription(), updatedSondage.getDescription());

        // Ajout d’un commentaire
        CommentaireDto commentaireDto = new CommentaireDto();
        commentaireDto.setParticipantId(participant2Id); // Utilisation de getParticipantId()
        commentaireDto.setCommentaire("Super sondage !");
        res = given()
                .contentType(ContentType.JSON)
                .body(commentaireDto)
                .when()
                .post(SONDAGE_BASE_PATH + sondageId + "/commentaires")
                .then()
                .statusCode(201)
                .extract()
                .response();
        CommentaireDto createdCommentaire = res.as(CommentaireDto.class);
        Long commentaireId = createdCommentaire.getCommentaireId();
        assertEquals(commentaireDto.getParticipantId(), createdCommentaire.getParticipantId());
        assertEquals(commentaireDto.getCommentaire(), createdCommentaire.getCommentaire());
        assertEquals(sondageId, createdCommentaire.getSondageId());

        // Récupération des commentaires
        res = given()
                .contentType(ContentType.JSON)
                .when()
                .get(SONDAGE_BASE_PATH + sondageId + "/commentaires")
                .then()
                .statusCode(200)
                .extract()
                .response();
        List<CommentaireDto> commentaires = res.jsonPath().getList(".", CommentaireDto.class);
        assertEquals(1, commentaires.size());
        CommentaireDto retrievedCommentaire = commentaires.get(0);
        assertEquals(commentaireId, retrievedCommentaire.getCommentaireId());
        assertEquals(commentaireDto.getCommentaire(), retrievedCommentaire.getCommentaire());
        assertEquals(commentaireDto.getParticipantId(), retrievedCommentaire.getParticipantId());

        // Ajout de dates au sondage
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, 1);
        Date date1 = calendar.getTime();
        calendar.add(Calendar.MINUTE, 1);
        Date date2 = calendar.getTime();

        DateSondageDto dateSondageDto1 = new DateSondageDto();
        dateSondageDto1.setDate(date1);
        res = given()
                .contentType(ContentType.JSON)
                .body(dateSondageDto1)
                .when()
                .post(SONDAGE_BASE_PATH + sondageId + "/dates")
                .then()
                .statusCode(201)
                .extract()
                .response();
        DateSondageDto createdDateSondage1 = res.as(DateSondageDto.class);
        Long dateSondage1Id = createdDateSondage1.getDateSondageId();

        DateSondageDto dateSondageDto2 = new DateSondageDto();
        dateSondageDto2.setDate(date2);
        res = given()
                .contentType(ContentType.JSON)
                .body(dateSondageDto2)
                .when()
                .post(SONDAGE_BASE_PATH + sondageId + "/dates")
                .then()
                .statusCode(201)
                .extract()
                .response();
        DateSondageDto createdDateSondage2 = res.as(DateSondageDto.class);
        Long dateSondage2Id = createdDateSondage2.getDateSondageId();

        // Récupération des dates
        res = given()
                .contentType(ContentType.JSON)
                .when()
                .get(SONDAGE_BASE_PATH + sondageId + "/dates")
                .then()
                .statusCode(200)
                .extract()
                .response();
        List<DateSondageDto> dates = res.jsonPath().getList(".", DateSondageDto.class);
        assertEquals(2, dates.size());
        assertEquals(dateSondage1Id, dates.get(0).getDateSondageId());
        assertEquals(date1, dates.get(0).getDate());
        assertEquals(dateSondage2Id, dates.get(1).getDateSondageId());
        assertEquals(date2, dates.get(1).getDate());

        // Participation aux dates
        DateSondeeDto dateSondeeDto1 = new DateSondeeDto();
        dateSondeeDto1.setParticipant(participant1Id);
        dateSondeeDto1.setChoix(String.valueOf(Choix.DISPONIBLE));
        given()
                .contentType(ContentType.JSON)
                .body(dateSondeeDto1)
                .when()
                .post(DATE_SONDAGE_BASE_PATH + dateSondage1Id + "/participer")
                .then()
                .statusCode(201);

        DateSondeeDto dateSondeeDto2 = new DateSondeeDto();
        dateSondeeDto2.setParticipant(participant1Id);
        dateSondeeDto2.setChoix(String.valueOf(Choix.PEUTETRE));
        given()
                .contentType(ContentType.JSON)
                .body(dateSondeeDto2)
                .when()
                .post(DATE_SONDAGE_BASE_PATH + dateSondage2Id + "/participer")
                .then()
                .statusCode(201);

        DateSondeeDto dateSondeeDto3 = new DateSondeeDto();
        dateSondeeDto3.setParticipant(participant2Id);
        dateSondeeDto3.setChoix(String.valueOf(Choix.DISPONIBLE));
        given()
                .contentType(ContentType.JSON)
                .body(dateSondeeDto3)
                .when()
                .post(DATE_SONDAGE_BASE_PATH + dateSondage1Id + "/participer")
                .then()
                .statusCode(201);

        DateSondeeDto dateSondeeDto4 = new DateSondeeDto();
        dateSondeeDto4.setParticipant(participant2Id);
        dateSondeeDto4.setChoix(String.valueOf(Choix.PEUTETRE));
        given()
                .contentType(ContentType.JSON)
                .body(dateSondeeDto4)
                .when()
                .post(DATE_SONDAGE_BASE_PATH + dateSondage2Id + "/participer")
                .then()
                .statusCode(201);

        // Récupération des meilleures dates (/best)
        res = given()
                .contentType(ContentType.JSON)
                .when()
                .get(SONDAGE_BASE_PATH + sondageId + "/best")
                .then()
                .statusCode(200)
                .extract()
                .response();
        List<String> bestDates = res.jsonPath().getList(".", String.class);
        assertEquals(1, bestDates.size());
        assertEquals(date1, new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse(bestDates.get(0)));

        // Récupération des éventuelles meilleures dates (/maybe)
        res = given()
                .contentType(ContentType.JSON)
                .when()
                .get(SONDAGE_BASE_PATH + sondageId + "/maybe")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();
        List<String> maybeDates = res.jsonPath().getList(".", String.class);
        assertEquals(2, maybeDates.size(), "Expected two 'maybe' dates, got: " + maybeDates);
        assertEquals(date1, new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse(maybeDates.get(0)));
        assertEquals(date2, new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse(maybeDates.get(1)));

        // Après avoir clôturé le sondage
        sondageDto.setCloture(true);
        given()
                .contentType(ContentType.JSON)
                .body(sondageDto)
                .when()
                .put(SONDAGE_BASE_PATH + sondageId)
                .then()
                .statusCode(200);

// Vérifier que bestDate renvoie 404 après clôture
        given()
                .contentType(ContentType.JSON)
                .when()
                .get(SONDAGE_BASE_PATH + sondageId + "/best")
                .then()
                .log().all() // Pour déboguer
                .statusCode(404); // Ligne 319

        // Suppression du sondage
        given()
                .contentType(ContentType.JSON)
                .when()
                .delete(SONDAGE_BASE_PATH + sondageId)
                .then()
                .statusCode(200);

        // Vérification que le sondage est supprimé
        given()
                .contentType(ContentType.JSON)
                .when()
                .get(SONDAGE_BASE_PATH + sondageId)
                .then()
                .statusCode(404); // Supposant que votre GlobalExceptionHandler renvoie 404

        // Nettoyage des participants
        given()
                .when()
                .delete(PARTICIPANT_BASE_PATH + participant1Id)
                .then()
                .statusCode(200);

        given()
                .when()
                .delete(PARTICIPANT_BASE_PATH + participant2Id)
                .then()
                .statusCode(200);

        // Vérification finale de la liste des sondages
        res = given()
                .contentType(ContentType.JSON)
                .when()
                .get(SONDAGE_BASE_PATH)
                .then()
                .statusCode(200)
                .extract()
                .response();
        sondages = res.jsonPath().getList(".", SondageDto.class);
        assertEquals(initialSize, sondages.size());
    }
}