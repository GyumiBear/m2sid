package E2E;

import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.MySurveyApplication;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.dtos.ParticipantDto;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Participant;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        classes = MySurveyApplication.class
)
@ActiveProfiles("test")
public class TestParticipantEndToEnd {

    private static final String PARTICIPANT_BASE_PATH = "/api/participant/";

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8081; // Port défini pour les tests
    }

    @Test
    void testCreateAndGetParticipant() {
        // Création d’un participant
        ParticipantDto participantDto = new ParticipantDto();
        participantDto.setNom("Dupont");
        participantDto.setPrenom("Jean");

        Long participantId = given()
                .contentType(ContentType.JSON)
                .body(participantDto)
                .when()
                .post(PARTICIPANT_BASE_PATH)
                .then()
                .log().ifError() // Log si statut != 201
                .statusCode(201)
                .extract()
                .as(ParticipantDto.class)
                .getParticipantId();

        // Vérification de la création
        assertNotNull(participantId);

        // Récupération du participant créé
        ParticipantDto retrievedParticipant = given()
                .when()
                .get(PARTICIPANT_BASE_PATH + participantId)
                .then()
                .log().ifError() // Log si statut != 200
                .statusCode(200)
                .extract()
                .as(ParticipantDto.class);

        // Vérifications
        assertEquals("Dupont", retrievedParticipant.getNom());
        assertEquals("Jean", retrievedParticipant.getPrenom());
        assertEquals(participantId, retrievedParticipant.getParticipantId());

        // Nettoyage
        given()
                .when()
                .delete(PARTICIPANT_BASE_PATH + participantId)
                .then()
                .log().ifError() // Log si statut != 200
                .statusCode(200);
    }

    @Test
    void testGetAllParticipants() {
        // Création d’un participant pour s’assurer qu’il y a au moins un élément
        ParticipantDto participantDto = new ParticipantDto();
        participantDto.setNom("Martin");
        participantDto.setPrenom("Sophie");

        Long participantId = given()
                .contentType(ContentType.JSON)
                .body(participantDto)
                .when()
                .post(PARTICIPANT_BASE_PATH)
                .then()
                .log().ifError() // Log si statut != 201
                .statusCode(201)
                .extract()
                .as(ParticipantDto.class)
                .getParticipantId();

        // Récupération de tous les participants
        Response response = given()
                .when()
                .get(PARTICIPANT_BASE_PATH)
                .then()
                .log().ifError() // Log si statut != 200
                .statusCode(200)
                .extract()
                .response();

        List<ParticipantDto> participants = response.jsonPath().getList(".", ParticipantDto.class);

        // Vérifications
        assertTrue(participants.size() >= 1); // Au moins notre participant créé + ceux de data.sql
        ParticipantDto createdParticipant = participants.stream()
                .filter(p -> p.getParticipantId().equals(participantId))
                .findFirst()
                .orElse(null);
        assertNotNull(createdParticipant);
        assertEquals("Martin", createdParticipant.getNom());
        assertEquals("Sophie", createdParticipant.getPrenom());

        // Nettoyage
        given()
                .when()
                .delete(PARTICIPANT_BASE_PATH + participantId)
                .then()
                .log().ifError() // Log si statut != 200
                .statusCode(200);
    }

    @Test
    void testUpdateParticipant() {
        // Création d’un participant
        ParticipantDto participantDto = new ParticipantDto();
        participantDto.setNom("Lefèvre");
        participantDto.setPrenom("Paul");

        Long participantId = given()
                .contentType(ContentType.JSON)
                .body(participantDto)
                .when()
                .post(PARTICIPANT_BASE_PATH)
                .then()
                .log().ifError() // Log si statut != 201
                .statusCode(201)
                .extract()
                .as(ParticipantDto.class)
                .getParticipantId();

        // Mise à jour du participant
        participantDto.setNom("Lefèvre Modifié");
        participantDto.setPrenom("Pauline");

        ParticipantDto updatedParticipant = given()
                .contentType(ContentType.JSON)
                .body(participantDto)
                .when()
                .put(PARTICIPANT_BASE_PATH + participantId)
                .then()
                .log().ifError() // Log si statut != 200
                .statusCode(200)
                .extract()
                .as(ParticipantDto.class);

        // Vérifications
        assertEquals("Lefèvre Modifié", updatedParticipant.getNom());
        assertEquals("Pauline", updatedParticipant.getPrenom());
        assertEquals(participantId, updatedParticipant.getParticipantId());

        // Nettoyage
        given()
                .when()
                .delete(PARTICIPANT_BASE_PATH + participantId)
                .then()
                .log().ifError() // Log si statut != 200
                .statusCode(200);
    }

    @Test
    void testDeleteParticipant() {
        // Création d’un participant
        ParticipantDto participantDto = new ParticipantDto();
        participantDto.setNom("Durand");
        participantDto.setPrenom("Marie");

        Long participantId = given()
                .contentType(ContentType.JSON)
                .body(participantDto)
                .when()
                .post(PARTICIPANT_BASE_PATH)
                .then()
                .log().ifError() // Log si statut != 201
                .statusCode(201)
                .extract()
                .as(ParticipantDto.class)
                .getParticipantId();

        // Suppression du participant
        given()
                .when()
                .delete(PARTICIPANT_BASE_PATH + participantId)
                .then()
                .log().ifError() // Log si statut != 200
                .statusCode(200);

        // Tentative de récupération pour vérifier la suppression
        given()
                .when()
                .get(PARTICIPANT_BASE_PATH + participantId)
                .then()
                .log().ifError() // Log si statut != 404 (ou autre statut d'erreur configuré)
                .statusCode(404); // On s’attend à un 404 car le participant est supprimé
    }

    @Test
    void testUpdateParticipantFromInitialData() {
        // Utilisation des données de data.sql (participant ID = 1)
        Long initialParticipantId = 1L;

        // Mise à jour du participant existant
        ParticipantDto participantDto = new ParticipantDto();
        participantDto.setParticipantId(initialParticipantId);
        participantDto.setNom("Doe Modifié");
        participantDto.setPrenom("Johnny");

        ParticipantDto updatedParticipant = given()
                .contentType(ContentType.JSON)
                .body(participantDto)
                .when()
                .put(PARTICIPANT_BASE_PATH + initialParticipantId)
                .then()
                .log().ifError() // Log si statut != 200
                .statusCode(200)
                .extract()
                .as(ParticipantDto.class);

        // Vérifications
        assertEquals("Doe Modifié", updatedParticipant.getNom());
        assertEquals("Johnny", updatedParticipant.getPrenom());
        assertEquals(initialParticipantId, updatedParticipant.getParticipantId());
    }
}