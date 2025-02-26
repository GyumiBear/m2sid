package unit_tests;

import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.dtos.CommentaireDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestCommentaireDto {
    private CommentaireDto dto;
    private CommentaireDto sameDto;
    private CommentaireDto differentDto;

    @BeforeEach
    void setUp() {
        dto = new CommentaireDto();
        dto.setCommentaireId(1L);
        dto.setCommentaire("Test comment");
        dto.setParticipant(100L);

        sameDto = new CommentaireDto();
        sameDto.setCommentaireId(1L);
        sameDto.setCommentaire("Test comment");
        sameDto.setParticipant(100L);

        differentDto = new CommentaireDto();
    }

    @Nested
    @DisplayName("Tests des getters/setters")
    class GetterSetterTests {

        @Test
        @DisplayName("Devrait correctement gérer commentaireId")
        void testCommentaireId() {
            dto.setCommentaireId(2L);
            assertEquals(2L, dto.getCommentaireId());
        }

        @Test
        @DisplayName("Devrait correctement gérer le commentaire")
        void testCommentaire() {
            dto.setCommentaire("New comment");
            assertEquals("New comment", dto.getCommentaire());
        }

        @Test
        @DisplayName("Devrait correctement gérer le participant")
        void testParticipant() {
            dto.setParticipant(200L);
            assertEquals(200L, dto.getParticipant());
        }
    }
}
