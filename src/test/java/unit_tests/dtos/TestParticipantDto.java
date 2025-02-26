package unit_tests.dtos;

import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.dtos.ParticipantDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TestParticipantDto {

    private ParticipantDto dto;
    private ParticipantDto sameDto;
    private ParticipantDto differentDto;

    @BeforeEach
    void setUp() {
        dto = new ParticipantDto();
        dto.setParticipantId(1L);
        dto.setNom("Dupont");
        dto.setPrenom("Jean");

        sameDto = new ParticipantDto();
        sameDto.setParticipantId(1L);
        sameDto.setNom("Dupont");
        sameDto.setPrenom("Jean");

        differentDto = new ParticipantDto();
    }

    @Nested
    @DisplayName("Tests des getters/setters")
    class GetterSetterTests {

        @Test
        @DisplayName("Devrait gérer correctement participantId")
        void testParticipantId() {
            dto.setParticipantId(2L);
            assertEquals(2L, dto.getParticipantId());
        }

        @Test
        @DisplayName("Devrait gérer correctement le nom")
        void testNom() {
            dto.setNom("Martin");
            assertEquals("Martin", dto.getNom());
        }

        @Test
        @DisplayName("Devrait gérer correctement le prénom")
        void testPrenom() {
            dto.setPrenom("Pierre");
            assertEquals("Pierre", dto.getPrenom());
        }

        @Test
        @DisplayName("Devrait accepter des valeurs null")
        void testNullValues() {
            dto.setNom(null);
            dto.setPrenom(null);
            assertAll(
                    () -> assertNull(dto.getNom()),
                    () -> assertNull(dto.getPrenom())
            );
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
        @DisplayName("Devrait être égal avec les mêmes valeurs")
        void testEqualsSameValues() {
            assertEquals(dto, sameDto);
        }

        @Test
        @DisplayName("Ne devrait pas être égal à null")
        void testEqualsNull() {
            assertNotEquals(dto, null);
        }

        @Test
        @DisplayName("Ne devrait pas être égal à un autre type d'objet")
        void testEqualsDifferentClass() {
            assertNotEquals(dto, new Object());
        }

        @Test
        @DisplayName("Ne devrait pas être égal si participantId différent")
        void testEqualsDifferentParticipantId() {
            differentDto.setParticipantId(2L);
            differentDto.setNom("Dupont");
            differentDto.setPrenom("Jean");
            assertNotEquals(dto, differentDto);
        }

        @Test
        @DisplayName("Ne devrait pas être égal si nom différent")
        void testEqualsDifferentNom() {
            differentDto.setParticipantId(1L);
            differentDto.setNom("Durand");
            differentDto.setPrenom("Jean");
            assertNotEquals(dto, differentDto);
        }

        @Test
        @DisplayName("Ne devrait pas être égal si prénom différent")
        void testEqualsDifferentPrenom() {
            differentDto.setParticipantId(1L);
            differentDto.setNom("Dupont");
            differentDto.setPrenom("Paul");
            assertNotEquals(dto, differentDto);
        }
    }

    @Nested
    @DisplayName("Tests de la méthode hashCode")
    class HashCodeTests {

        @Test
        @DisplayName("Hashcode devrait être cohérent pour des objets égaux")
        void testHashCodeConsistency() {
            assertEquals(dto.hashCode(), sameDto.hashCode());
        }

        @Test
        @DisplayName("Hashcode devrait être différent pour des valeurs différentes")
        void testHashCodeDifference() {
            assertNotEquals(dto.hashCode(), differentDto.hashCode());
        }

        @Test
        @DisplayName("Hashcode devrait rester stable entre plusieurs appels")
        void testHashCodeStability() {
            int initialHash = dto.hashCode();
            assertEquals(initialHash, dto.hashCode());
        }

        @Test
        @DisplayName("Hashcode avec valeurs null devrait fonctionner")
        void testHashCodeWithNullValues() {
            ParticipantDto nullDto = new ParticipantDto();
            assertDoesNotThrow(nullDto::hashCode);
        }
    }

    @Nested
    @DisplayName("Tests de la méthode toString")
    class ToStringTests {

        @Test
        @DisplayName("Devrait contenir toutes les informations")
        void testToStringContent() {
            String result = dto.toString();
            assertAll(
                    () -> assertTrue(result.contains("participantId=1")),
                    () -> assertTrue(result.contains("nom='Dupont'")),
                    () -> assertTrue(result.contains("prenom='Jean'"))
            );
        }

        @Test
        @DisplayName("Devrait gérer les string vides")
        void testToStringWithEmptyStringValues() {
            ParticipantDto nullDto = new ParticipantDto();
            String result = nullDto.toString();
            assertAll(
                    () -> assertTrue(result.contains("participantId=")),
                    () -> assertTrue(result.contains("nom=")),
                    () -> assertTrue(result.contains("prenom="))
            );
        }
    }


}