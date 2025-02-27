package unit_tests.dtos;

import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.dtos.SondageDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TestSondageDto {

    private SondageDto dto;
    private SondageDto sameDto;
    private SondageDto differentDto;
    private Date fixedDate;

    @BeforeEach
    void setUp() {
        fixedDate = new Date();

        dto = new SondageDto();
        dto.setSondageId(1L);
        dto.setNom("Sondage Test");
        dto.setDescription("Description Test");
        dto.setFin(fixedDate);
        dto.setCloture(true);
        dto.setCreateBy(100L);

        sameDto = new SondageDto();
        sameDto.setSondageId(1L);
        sameDto.setNom("Sondage Test");
        sameDto.setDescription("Description Test");
        sameDto.setFin(fixedDate);
        sameDto.setCloture(true);
        sameDto.setCreateBy(100L);

        differentDto = new SondageDto();
    }

    @Nested
    @DisplayName("Tests des getters/setters")
    class GetterSetterTests {

        @Test
        @DisplayName("Devrait gérer correctement sondageId")
        void testSondageId() {
            dto.setSondageId(2L);
            assertEquals(2L, dto.getSondageId());
        }

        @Test
        @DisplayName("Devrait gérer correctement le nom")
        void testNom() {
            dto.setNom("Nouveau nom");
            assertEquals("Nouveau nom", dto.getNom());
        }

        @Test
        @DisplayName("Devrait gérer correctement la description")
        void testDescription() {
            dto.setDescription("Nouvelle description");
            assertEquals("Nouvelle description", dto.getDescription());
        }

        @Test
        @DisplayName("Devrait gérer correctement la date de fin")
        void testFin() {
            Date newDate = new Date();
            dto.setFin(newDate);
            assertEquals(newDate, dto.getFin());
        }

        @Test
        @DisplayName("Devrait gérer correctement l'état de clôture")
        void testCloture() {
            dto.setCloture(false);
            assertFalse(dto.getCloture());
        }

        @Test
        @DisplayName("Devrait gérer correctement le créateur")
        void testCreateBy() {
            dto.setCreateBy(200L);
            assertEquals(200L, dto.getCreateBy());
        }

        @Test
        @DisplayName("Devrait accepter des valeurs null")
        void testNullValues() {
            dto.setNom(null);
            dto.setDescription(null);
            dto.setFin(null);

            assertAll(
                    () -> assertNull(dto.getNom()),
                    () -> assertNull(dto.getDescription()),
                    () -> assertNull(dto.getFin())
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
        @DisplayName("Ne devrait pas être égal si sondageId différent")
        void testDifferentSondageId() {
            configureSameOtherFields(differentDto); // D'abord configurer toutes les valeurs
            differentDto.setSondageId(2L); // Puis modifier SEULEMENT le champ testé
            assertNotEquals(dto, differentDto);
        }

        @Test
        @DisplayName("Ne devrait pas être égal si nom différent")
        void testDifferentNom() {
            configureSameOtherFields(differentDto);
            differentDto.setNom("Autre nom");
            assertNotEquals(dto, differentDto);
        }

        @Test
        @DisplayName("Ne devrait pas être égal si description différente")
        void testDifferentDescription() {
            configureSameOtherFields(differentDto);
            differentDto.setDescription("Autre description");
            assertNotEquals(dto, differentDto);
        }

        @Test
        @DisplayName("Ne devrait pas être égal si date de fin différente")
        void testDifferentFin() {
            configureSameOtherFields(differentDto);
            differentDto.setFin(new Date(fixedDate.getTime() + 1000));
            assertNotEquals(dto, differentDto);
        }

        @Test
        @DisplayName("Ne devrait pas être égal si clôture différente")
        void testDifferentCloture() {
            configureSameOtherFields(differentDto);
            differentDto.setCloture(false);
            assertNotEquals(dto, differentDto);
        }

        @Test
        @DisplayName("Ne devrait pas être égal si créateur différent")
        void testDifferentCreateBy() {
            configureSameOtherFields(differentDto);
            differentDto.setCreateBy(200L);
            assertNotEquals(dto, differentDto);
        }

        private void configureSameOtherFields(SondageDto dto) {
            dto.setSondageId(1L);
            dto.setNom("Sondage Test");
            dto.setDescription("Description Test");
            dto.setFin(fixedDate);
            dto.setCloture(true);
            dto.setCreateBy(100L);
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
            SondageDto nullDto = new SondageDto();
            assertDoesNotThrow(nullDto::hashCode);
        }
    }

    @Test
    @DisplayName("Cohérence entre equals() et hashCode()")
    void testEqualsAndHashCodeConsistency() {
        // Mêmes valeurs -> même hashcode
        assertEquals(dto, sameDto);
        assertEquals(dto.hashCode(), sameDto.hashCode());

        // Valeurs différentes -> hashcode différent
        differentDto.setSondageId(2L);
        assertNotEquals(dto, differentDto);
        assertNotEquals(dto.hashCode(), differentDto.hashCode());
    }
}