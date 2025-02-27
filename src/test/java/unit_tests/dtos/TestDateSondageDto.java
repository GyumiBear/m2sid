package unit_tests.dtos;

import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.dtos.DateSondageDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TestDateSondageDto {

    private DateSondageDto dto;
    private DateSondageDto sameDto;
    private DateSondageDto differentDto;
    private Date fixedDate;

    @BeforeEach
    void setUp() {
        fixedDate = new Date();

        dto = new DateSondageDto();
        dto.setDateSondageId(1L);
        dto.setDate(fixedDate);

        sameDto = new DateSondageDto();
        sameDto.setDateSondageId(1L);
        sameDto.setDate(fixedDate);

        differentDto = new DateSondageDto();
    }

    @Nested
    @DisplayName("Tests des getters/setters")
    class GetterSetterTests {

        @Test
        @DisplayName("getDateSondageId devrait retourner la valeur définie")
        void testGetSetDateSondageId() {
            dto.setDateSondageId(2L);
            assertEquals(2L, dto.getDateSondageId());
        }

        @Test
        @DisplayName("getDate devrait retourner la date définie")
        void testGetSetDate() {
            Date newDate = new Date();
            dto.setDate(newDate);
            assertEquals(newDate, dto.getDate());
        }

        @Test
        @DisplayName("setDate avec null devrait être possible")
        void testSetNullDate() {
            dto.setDate(null);
            assertNull(dto.getDate());
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
        @DisplayName("Ne devrait pas être égal à un objet d'une autre classe")
        void testEqualsDifferentClass() {
            assertNotEquals(dto, new Object());
        }

        @Test
        @DisplayName("Ne devrait pas être égal quand dateSondageId diffère")
        void testEqualsDifferentId() {
            differentDto.setDateSondageId(2L);
            differentDto.setDate(fixedDate);
            assertNotEquals(dto, differentDto);
        }

        @Test
        @DisplayName("Ne devrait pas être égal quand la date diffère")
        void testEqualsDifferentDate() {
            differentDto.setDateSondageId(1L);
            differentDto.setDate(new Date(fixedDate.getTime() + 1000));
            assertNotEquals(dto, differentDto);
        }

        @Test
        @DisplayName("Ne devrait pas être égal quand une date est null")
        void testEqualsWithNullDate() {
            DateSondageDto nullDateDto = new DateSondageDto();
            nullDateDto.setDateSondageId(1L);
            assertNotEquals(dto, nullDateDto);
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
        @DisplayName("Hashcode devrait être différent pour des propriétés différentes")
        void testHashCodeDifference() {
            assertNotEquals(dto.hashCode(), differentDto.hashCode());
        }

        @Test
        @DisplayName("Hashcode devrait être stable entre plusieurs appels")
        void testHashCodeStability() {
            int initialHash = dto.hashCode();
            assertEquals(initialHash, dto.hashCode());
        }

        @Test
        @DisplayName("Hashcode avec date null devrait fonctionner")
        void testHashCodeWithNullDate() {
            DateSondageDto nullDateDto = new DateSondageDto();
            nullDateDto.setDateSondageId(1L);
            assertDoesNotThrow(nullDateDto::hashCode);
        }
    }

    @Test
    @DisplayName("Cohérence entre equals et hashCode")
    void testEqualsAndHashCodeConsistency() {
        // Mêmes valeurs -> même hashcode
        assertEquals(dto, sameDto);
        assertEquals(dto.hashCode(), sameDto.hashCode());

        // Valeurs différentes -> hashcode différent
        differentDto.setDateSondageId(2L);
        assertNotEquals(dto, differentDto);
        assertNotEquals(dto.hashCode(), differentDto.hashCode());
    }
}