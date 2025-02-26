package unit_tests.dtos;

import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.dtos.DateSondeeDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TestDateSondeeDto{

    private DateSondeeDto dto;
    private DateSondeeDto sameDto;
    private DateSondeeDto differentDto;

    @BeforeEach
    void setUp() {
        dto = new DateSondeeDto();
        dto.setDateSondeeId(1L);
        dto.setParticipant(100L);
        dto.setChoix("DISPONIBLE");

        sameDto = new DateSondeeDto();
        sameDto.setDateSondeeId(1L);
        sameDto.setParticipant(100L);
        sameDto.setChoix("DISPONIBLE");

        differentDto = new DateSondeeDto();
    }

    @Nested
    @DisplayName("Tests des getters/setters")
    class GetterSetterTests {

        @Test
        @DisplayName("Devrait gérer correctement dateSondeeId")
        void testDateSondeeId() {
            dto.setDateSondeeId(2L);
            assertEquals(2L, dto.getDateSondeeId());
        }

        @Test
        @DisplayName("Devrait gérer correctement le participant")
        void testParticipant() {
            dto.setParticipant(200L);
            assertEquals(200L, dto.getParticipant());
        }

        @Test
        @DisplayName("Devrait gérer correctement le choix")
        void testChoix() {
            dto.setChoix("INDISPONIBLE");
            assertEquals("INDISPONIBLE", dto.getChoix());
        }

        @Test
        @DisplayName("Devrait accepter une valeur null pour le choix")
        void testNullChoix() {
            dto.setChoix(null);
            assertNull(dto.getChoix());
        }
    }

}