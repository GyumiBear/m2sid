package unit_tests.dtos;

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
        dto.setParticipantId(100L);

        sameDto = new CommentaireDto();
        sameDto.setCommentaireId(1L);
        sameDto.setCommentaire("Test comment");
        sameDto.setParticipantId(100L);

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
            dto.setParticipantId(200L);
            assertEquals(200L, dto.getParticipantId());
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
            differentDto.setParticipantId(100L);
            assertNotEquals(dto, differentDto);
        }
    }

    @Nested
    @DisplayName("Tests de la méthode hashCode")
    class HashCodeTests {

        @Test
        @DisplayName("Devrait avoir le même hashcode pour des instances égales")
        void testHashCodeConsistency() {
            assertEquals(dto.hashCode(), sameDto.hashCode());
        }

        @Test
        @DisplayName("Devrait avoir des hashcodes différents quand les propriétés diffèrent")
        void testHashCodeDifference() {
            assertNotEquals(dto.hashCode(), differentDto.hashCode());
        }

        @Test
        @DisplayName("Hashcode devrait être cohérent entre plusieurs appels")
        void testHashCodeSameInstance() {
            int initialHashCode = dto.hashCode();
            assertEquals(initialHashCode, dto.hashCode());
        }
    }

    @Test
    @DisplayName("Test de la cohérence entre equals et hashCode")
    void testEqualsAndHashCodeConsistency() {
        // Si deux objets sont égaux, leurs hashcodes doivent être égaux
        assertTrue(dto.equals(sameDto));
        assertEquals(dto.hashCode(), sameDto.hashCode());

        // Si deux objets ne sont pas égaux, leurs hashcodes devraient idéalement être différents
        assertFalse(dto.equals(differentDto));
        assertNotEquals(dto.hashCode(), differentDto.hashCode());
    }
}
