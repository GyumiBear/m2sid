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
}