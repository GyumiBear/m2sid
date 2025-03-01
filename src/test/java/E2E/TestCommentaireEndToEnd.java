package E2E;

import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.MySurveyApplication;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.dtos.CommentaireDto;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.dtos.ParticipantDto;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.dtos.SondageDto;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Participant;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        classes = MySurveyApplication.class
)
@ActiveProfiles("test")
public class TestCommentaireEndToEnd {

    private static final String API_BASE_PATH = "/api/commentaire/";
    private static final String SONDAGE_BASE_PATH = "/api/sondage/";
    private static final String PARTICIPANT_BASE_PATH = "/api/participant/";

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8081; // Port défini pour les tests
    }

    @Test
    void commentaireTestEndToEnd() {
        // Création du participant
        ParticipantDto participantDto = new ParticipantDto();
        participantDto.setNom("Baba");
        participantDto.setPrenom("Car");
        Long participantId = given()
                .contentType(ContentType.JSON)
                .body(participantDto)
                .when()
                .post(PARTICIPANT_BASE_PATH)
                .then()
                .log().ifError() // Log si statut != 201
                .statusCode(201)
                .extract()
                .as(Participant.class)
                .getParticipantId();

        // Création du sondage
        SondageDto sondageDto = new SondageDto();
        sondageDto.setCreateBy(participantId);
        sondageDto.setNom("Sondage Test");
        Long sondageId = given()
                .contentType(ContentType.JSON)
                .body(sondageDto)
                .when()
                .post(SONDAGE_BASE_PATH)
                .then()
                .log().ifError() // Log si statut != 201
                .statusCode(201)
                .extract()
                .as(SondageDto.class)
                .getSondageId();

        // Création du commentaire
        CommentaireDto commentaireDto = new CommentaireDto();
        commentaireDto.setParticipantId(participantId);
        commentaireDto.setCommentaire("Commentaire Test");
        commentaireDto.setSondageId(sondageId);
        Long commentaireId = given()
                .contentType(ContentType.JSON)
                .body(commentaireDto)
                .when()
                .post(SONDAGE_BASE_PATH + sondageId + "/commentaires")
                .then()
                .log().ifError() // Log si statut != 201
                .statusCode(201)
                .extract()
                .as(CommentaireDto.class)
                .getCommentaireId();

        // Mise à jour du commentaire
        commentaireDto.setCommentaire("Commentaire Test Modifié");
        commentaireDto.setSondageId(sondageId);
        CommentaireDto updatedCommentaire = given()
                .contentType(ContentType.JSON)
                .body(commentaireDto)
                .when()
                .put(API_BASE_PATH + commentaireId)
                .then()
                .log().ifError() // Log si statut != 200
                .statusCode(200)
                .extract()
                .as(CommentaireDto.class);

        assertEquals("Commentaire Test Modifié", updatedCommentaire.getCommentaire());
        assertEquals(participantId, updatedCommentaire.getParticipantId());

        // Nettoyage
        given().delete(API_BASE_PATH + commentaireId)
                .then()
                .log().ifError() // Log si statut != 200
                .statusCode(200);
        given().delete(SONDAGE_BASE_PATH + sondageId)
                .then()
                .log().ifError() // Log si statut != 200
                .statusCode(200);
        given().delete(PARTICIPANT_BASE_PATH + participantId)
                .then()
                .log().ifError() // Log si statut != 200
                .statusCode(200);
    }

    @Test
    void updateCommentaireFromInitialData() {
        // Utilisation des données de data.sql (participant ID = 1, sondage ID = 1, commentaire ID = 1)
        Long initialCommentaireId = 1L;
        Long initialParticipantId = 1L;
        Long initialSondageId = 1L;

        // Mise à jour du commentaire existant
        CommentaireDto commentaireDto = new CommentaireDto();
        commentaireDto.setCommentaireId(initialCommentaireId);
        commentaireDto.setCommentaire("Commentaire Initial Modifié");
        commentaireDto.setParticipantId(initialParticipantId);
        commentaireDto.setSondageId(initialSondageId);

        CommentaireDto updatedCommentaire = given()
                .contentType(ContentType.JSON)
                .body(commentaireDto)
                .when()
                .put(API_BASE_PATH + initialCommentaireId)
                .then()
                .log().ifError() // Log si statut != 200
                .statusCode(200)
                .extract()
                .as(CommentaireDto.class);

        // Vérifications
        assertEquals("Commentaire Initial Modifié", updatedCommentaire.getCommentaire());
        assertEquals(initialParticipantId, updatedCommentaire.getParticipantId());
        assertEquals(initialSondageId, updatedCommentaire.getSondageId());
    }

    @Test
    void createAndDeleteCommentaire() {
        // Création du participant
        ParticipantDto participantDto = new ParticipantDto();
        participantDto.setNom("Best");
        participantDto.setPrenom("Practices");
        Long participantId = given()
                .contentType(ContentType.JSON)
                .body(participantDto)
                .when()
                .post(PARTICIPANT_BASE_PATH)
                .then()
                .log().ifError() // Log si statut != 201
                .statusCode(201)
                .extract()
                .as(Participant.class)
                .getParticipantId();

        // Création du sondage
        SondageDto sondageDto = new SondageDto();
        sondageDto.setCreateBy(participantId);
        sondageDto.setNom("Sondage pour Suppression");
        Long sondageId = given()
                .contentType(ContentType.JSON)
                .body(sondageDto)
                .when()
                .post(SONDAGE_BASE_PATH)
                .then()
                .log().ifError() // Log si statut != 201
                .statusCode(201)
                .extract()
                .as(SondageDto.class)
                .getSondageId();

        // Création du commentaire
        CommentaireDto commentaireDto = new CommentaireDto();
        commentaireDto.setParticipantId(participantId);
        commentaireDto.setSondageId(sondageId);
        commentaireDto.setCommentaire("À supprimer");
        Long commentaireId = given()
                .contentType(ContentType.JSON)
                .body(commentaireDto)
                .when()
                .post(SONDAGE_BASE_PATH + sondageId + "/commentaires")
                .then()
                .log().ifError() // Log si statut != 201
                .statusCode(201)
                .extract()
                .as(CommentaireDto.class)
                .getCommentaireId();

        // Vérification de la création
        assertNotNull(commentaireId);

        // Suppression du commentaire
        given()
                .when()
                .delete(API_BASE_PATH + commentaireId)
                .then()
                .log().ifError() // Log si statut != 200
                .statusCode(200);

        // Nettoyage
        given().delete(SONDAGE_BASE_PATH + sondageId)
                .then()
                .log().ifError() // Log si statut != 200
                .statusCode(200);
        given().delete(PARTICIPANT_BASE_PATH + participantId)
                .then()
                .log().ifError() // Log si statut != 200
                .statusCode(200);
    }

    @Test
    void updateCommentaireTextOnly() {
        // Création du participant
        ParticipantDto participantDto = new ParticipantDto();
        participantDto.setNom("Partial");
        participantDto.setPrenom("Update");
        Long participantId = given()
                .contentType(ContentType.JSON)
                .body(participantDto)
                .when()
                .post(PARTICIPANT_BASE_PATH)
                .then()
                .log().ifError() // Log si statut != 201
                .statusCode(201)
                .extract()
                .as(Participant.class)
                .getParticipantId();

        // Création du sondage
        SondageDto sondageDto = new SondageDto();
        sondageDto.setCreateBy(participantId);
        sondageDto.setNom("Sondage Partial");
        Long sondageId = given()
                .contentType(ContentType.JSON)
                .body(sondageDto)
                .when()
                .post(SONDAGE_BASE_PATH)
                .then()
                .log().ifError() // Log si statut != 201
                .statusCode(201)
                .extract()
                .as(SondageDto.class)
                .getSondageId();

        // Création du commentaire
        CommentaireDto commentaireDto = new CommentaireDto();
        commentaireDto.setParticipantId(participantId);
        commentaireDto.setSondageId(sondageId);
        commentaireDto.setCommentaire("Texte Initial");
        Long commentaireId = given()
                .contentType(ContentType.JSON)
                .body(commentaireDto)
                .when()
                .post(SONDAGE_BASE_PATH + sondageId + "/commentaires")
                .then()
                .log().ifError() // Log si statut != 201
                .statusCode(201)
                .extract()
                .as(CommentaireDto.class)
                .getCommentaireId();

        // Mise à jour partielle (seulement le texte)
        CommentaireDto partialUpdateDto = new CommentaireDto();
        partialUpdateDto.setCommentaire("Texte Modifié");
        partialUpdateDto.setCommentaireId(commentaireId);
        partialUpdateDto.setSondageId(sondageId);
        partialUpdateDto.setParticipantId(participantId);
        CommentaireDto updatedCommentaire = given()
                .contentType(ContentType.JSON)
                .body(partialUpdateDto)
                .when()
                .put(API_BASE_PATH + commentaireId)
                .then()
                .log().ifError() // Log si statut != 200
                .statusCode(200)
                .extract()
                .as(CommentaireDto.class);

        // Vérifications
        assertEquals("Texte Modifié", updatedCommentaire.getCommentaire());
        assertEquals(participantId, updatedCommentaire.getParticipantId());
        assertEquals(sondageId, updatedCommentaire.getSondageId());

        // Nettoyage
        given().delete(API_BASE_PATH + commentaireId)
                .then()
                .log().ifError() // Log si statut != 200
                .statusCode(200);
        given().delete(SONDAGE_BASE_PATH + sondageId)
                .then()
                .log().ifError() // Log si statut != 200
                .statusCode(200);
        given().delete(PARTICIPANT_BASE_PATH + participantId)
                .then()
                .log().ifError() // Log si statut != 200
                .statusCode(200);
    }
}