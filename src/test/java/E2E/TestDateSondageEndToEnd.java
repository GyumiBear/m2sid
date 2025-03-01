package E2E;

import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.MySurveyApplication;
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

import java.util.Date;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        classes = MySurveyApplication.class
)
@ActiveProfiles("test")
public class TestDateSondageEndToEnd {

    private static final String DATE_SONDAGE_BASE_PATH = "/api/date/";
    private static final String PARTICIPANT_BASE_PATH = "/api/participant/";
    private static final String SONDAGE_BASE_PATH = "/api/sondage/";

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8081; // Aligné sur vos tests précédents
    }

    @Test
    void testDeleteDateSondage() {
        // Création d’un participant
        ParticipantDto participantDto = new ParticipantDto();
        participantDto.setNom("Dupont");
        participantDto.setPrenom("Jean");

        Response participantResponse = given()
                .contentType(ContentType.JSON)
                .body(participantDto)
                .when()
                .post(PARTICIPANT_BASE_PATH)
                .then()
                .statusCode(201)
                .extract()
                .response();
        ParticipantDto createdParticipant = participantResponse.as(ParticipantDto.class);
        Long participantId = createdParticipant.getParticipantId();

        // Création d’un sondage
        SondageDto sondageDto = new SondageDto();
        sondageDto.setCreateBy(participantId);
        sondageDto.setNom("Sondage Test");
        sondageDto.setCloture(false);
        sondageDto.setFin(new Date());
        sondageDto.setDescription("Description");

        Response sondageResponse = given()
                .contentType(ContentType.JSON)
                .body(sondageDto)
                .when()
                .post(SONDAGE_BASE_PATH)
                .then()
                .statusCode(201)
                .extract()
                .response();
        SondageDto createdSondage = sondageResponse.as(SondageDto.class);
        Long sondageId = createdSondage.getSondageId();

        // Création d’une DateSondage
        DateSondageDto dateSondageDto = new DateSondageDto();
        dateSondageDto.setDate(new Date());

        Response dateSondageResponse = given()
                .contentType(ContentType.JSON)
                .body(dateSondageDto)
                .when()
                .post(SONDAGE_BASE_PATH + sondageId + "/dates/")
                .then()
                .statusCode(201)
                .extract()
                .response();
        DateSondageDto createdDateSondage = dateSondageResponse.as(DateSondageDto.class);
        Long dateSondageId = createdDateSondage.getDateSondageId();

        // Suppression de la DateSondage
        given()
                .contentType(ContentType.JSON)
                .when()
                .delete(DATE_SONDAGE_BASE_PATH + dateSondageId)
                .then()
                .statusCode(200);

        // Vérification que la DateSondage est supprimée (tentative de création d’une participation)
        DateSondeeDto dateSondeeDto = new DateSondeeDto();
        dateSondeeDto.setParticipant(participantId);
        dateSondeeDto.setChoix(String.valueOf(Choix.DISPONIBLE));

        given()
                .contentType(ContentType.JSON)
                .body(dateSondeeDto)
                .when()
                .post(DATE_SONDAGE_BASE_PATH + dateSondageId + "/participer/")
                .then()
                .statusCode(404); // DateSondage n’existe plus

        // Nettoyage
        given()
                .when()
                .delete(SONDAGE_BASE_PATH + sondageId)
                .then()
                .statusCode(200);

        given()
                .when()
                .delete(PARTICIPANT_BASE_PATH + participantId)
                .then()
                .statusCode(200);
    }

    @Test
    void testCreateParticipation() {
        // Création d’un participant
        ParticipantDto participantDto = new ParticipantDto();
        participantDto.setNom("Martin");
        participantDto.setPrenom("Sophie");

        Response participantResponse = given()
                .contentType(ContentType.JSON)
                .body(participantDto)
                .when()
                .post(PARTICIPANT_BASE_PATH)
                .then()
                .statusCode(201)
                .extract()
                .response();
        ParticipantDto createdParticipant = participantResponse.as(ParticipantDto.class);
        Long participantId = createdParticipant.getParticipantId();

        // Création d’un sondage
        SondageDto sondageDto = new SondageDto();
        sondageDto.setCreateBy(participantId);
        sondageDto.setNom("Sondage Test");
        sondageDto.setCloture(false);
        sondageDto.setFin(new Date());
        sondageDto.setDescription("Description");

        Response sondageResponse = given()
                .contentType(ContentType.JSON)
                .body(sondageDto)
                .when()
                .post(SONDAGE_BASE_PATH)
                .then()
                .statusCode(201)
                .extract()
                .response();
        SondageDto createdSondage = sondageResponse.as(SondageDto.class);
        Long sondageId = createdSondage.getSondageId();

        // Création d’une DateSondage
        DateSondageDto dateSondageDto = new DateSondageDto();
        dateSondageDto.setDate(new Date());

        Response dateSondageResponse = given()
                .contentType(ContentType.JSON)
                .body(dateSondageDto)
                .when()
                .post(SONDAGE_BASE_PATH + sondageId + "/dates/")
                .then()
                .statusCode(201)
                .extract()
                .response();
        DateSondageDto createdDateSondage = dateSondageResponse.as(DateSondageDto.class);
        Long dateSondageId = createdDateSondage.getDateSondageId();

        // Création d’une DateSondee
        DateSondeeDto dateSondeeDto = new DateSondeeDto();
        dateSondeeDto.setParticipant(participantId);
        dateSondeeDto.setChoix(String.valueOf(Choix.DISPONIBLE));

        Response dateSondeeResponse = given()
                .contentType(ContentType.JSON)
                .body(dateSondeeDto)
                .when()
                .post(DATE_SONDAGE_BASE_PATH + dateSondageId + "/participer/")
                .then()
                .statusCode(201)
                .extract()
                .response();
        DateSondeeDto createdDateSondee = dateSondeeResponse.as(DateSondeeDto.class);

        // Vérifications
        assertNotNull(createdDateSondee.getDateSondeeId());
        assertEquals(participantId, createdDateSondee.getParticipant());
        assertEquals(String.valueOf(Choix.DISPONIBLE), createdDateSondee.getChoix());

        // Nettoyage
        given()
                .when()
                .delete(SONDAGE_BASE_PATH + sondageId)
                .then()
                .statusCode(200);

        given()
                .when()
                .delete(PARTICIPANT_BASE_PATH + participantId)
                .then()
                .statusCode(200);
    }
}