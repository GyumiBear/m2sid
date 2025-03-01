package fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.repositories;

import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.DateSondee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface DateSondeeRepository extends JpaRepository<DateSondee, Long> {
    @Query(value = "SELECT d.date " +
            "FROM (SELECT MAX(nb) AS max_nb " +
            "      FROM (SELECT date_sondage_id, COUNT(choix) AS nb " +
            "            FROM date_sondee " +
            "            WHERE choix = 'DISPONIBLE' " +
            "            GROUP BY date_sondage_id) f) f1, " +
            "(SELECT date_sondage_id, COUNT(choix) AS nb " +
            " FROM date_sondee " +
            " WHERE choix = 'DISPONIBLE' " +
            " GROUP BY date_sondage_id) f2, " +
            "date_sondage d " +
            "WHERE f2.nb = f1.max_nb " +
            "AND d.date_sondage_id = f2.date_sondage_id " +
            "AND d.sondage_id = :sondageId",
            nativeQuery = true)
    List<Date> bestDate(@Param("sondageId") Long sondageId);

    @Query(value = "SELECT d.date " +
            "FROM (SELECT MAX(nb) AS max_nb " +
            "      FROM (SELECT date_sondage_id, COUNT(choix) AS nb " +
            "            FROM date_sondee " +
            "            WHERE choix IN ('DISPONIBLE', 'PEUTETRE') " +
            "            GROUP BY date_sondage_id) f) f1, " +
            "(SELECT date_sondage_id, COUNT(choix) AS nb " +
            " FROM date_sondee " +
            " WHERE choix IN ('DISPONIBLE', 'PEUTETRE') " +
            " GROUP BY date_sondage_id) f2, " +
            "date_sondage d " +
            "WHERE f2.nb = f1.max_nb " +
            "AND d.date_sondage_id = f2.date_sondage_id " +
            "AND d.sondage_id = :sondageId",
            nativeQuery = true)
    List<Date> maybeBestDate(@Param("sondageId") Long sondageId);
}