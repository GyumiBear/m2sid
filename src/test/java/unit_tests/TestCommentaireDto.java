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

    @Nested
    @DisplayName("Tests de la méthode equals")
    class EqualsTests {

        @Test
        @DisplayName("Devrait être égal à lui-même")
        void testEqualsSameInstance() {
            assertEquals(dto, dto);
        }

        @Test
        @DisplayName("Devrait être égal quand toutes les propriétés sont identiques")
        void testEqualsSameValues() {
            assertEquals(dto, sameDto);
        }

        @Test
        @DisplayName("Ne devrait pas être égal à null")
        void testEqualsNull() {
            assertNotEquals(dto, null);
        }

        @Test
        @DisplayName("Ne devrait pas être égal à un objet d'une classe différente")
        void testEqualsDifferentClass() {
            assertNotEquals(dto, new Object());
        }

        @Test
        @DisplayName("Ne devrait pas être égal quand commentaireId diffère")
        void testEqualsDifferentCommentaireId() {
            differentDto.setCommentaireId(2L);
            differentDto.setCommentaire("Test comment");
            differentDto.setParticipant(100L);
            assertNotEquals(dto, differentDto);
        }
    }
}
