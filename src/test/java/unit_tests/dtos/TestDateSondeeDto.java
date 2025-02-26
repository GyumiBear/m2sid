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
        @DisplayName("Ne devrait pas être égal si dateSondeeId différent")
        void testEqualsDifferentDateSondeeId() {
            differentDto.setDateSondeeId(2L);
            differentDto.setParticipant(100L);
            differentDto.setChoix("DISPONIBLE");
            assertNotEquals(dto, differentDto);
        }

        @Test
        @DisplayName("Ne devrait pas être égal si participant différent")
        void testEqualsDifferentParticipant() {
            differentDto.setDateSondeeId(1L);
            differentDto.setParticipant(200L);
            differentDto.setChoix("DISPONIBLE");
            assertNotEquals(dto, differentDto);
        }

        @Test
        @DisplayName("Ne devrait pas être égal si choix différent")
        void testEqualsDifferentChoix() {
            differentDto.setDateSondeeId(1L);
            differentDto.setParticipant(100L);
            differentDto.setChoix("INDISPONIBLE");
            assertNotEquals(dto, differentDto);
        }

        @Test
        @DisplayName("Ne devrait pas être égal si un champ est null")
        void testEqualsWithNullField() {
            DateSondeeDto nullDto = new DateSondeeDto();
            nullDto.setDateSondeeId(1L);
            assertNotEquals(dto, nullDto);
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
        @DisplayName("Hashcode devrait gérer les null values")
        void testHashCodeWithNullValues() {
            DateSondeeDto nullDto = new DateSondeeDto();
            assertDoesNotThrow(nullDto::hashCode);
        }
    }
}