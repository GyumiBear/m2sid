package fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services;

import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.DateSondage;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.DateSondee;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Sondage;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.repositories.DateSondeeRepository;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services.DateSondageService;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services.ParticipantService;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services.SondageService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class DateSondeeService {

    private final DateSondeeRepository repository;
    private final DateSondageService sdate;
    private final ParticipantService ps;
    private final SondageService sondageService; // Ajouter pour vérifier l'état du sondage

    public DateSondeeService(DateSondeeRepository repository, DateSondageService d, ParticipantService p, SondageService sondageService) {
        this.repository = repository;
        this.sdate = d;
        this.ps = p;
        this.sondageService = sondageService;
    }

    public DateSondee create(Long id, Long participantId, DateSondee dateSondee) {
        DateSondage date = sdate.getById(id);
        if (Boolean.FALSE.equals(date.getSondage().getCloture())) {
            dateSondee.setDateSondage(date);
            dateSondee.setParticipant(ps.getById(participantId));
            return repository.save(dateSondee);
        }
        return null;
    }

    public List<Date> bestDate(Long id) {
        Sondage sondage = sondageService.getById(id);
        if (sondage == null || Boolean.TRUE.equals(sondage.getCloture())) {
            throw new EntityNotFoundException("Sondage with id " + id + " not found or closed");
        }
        try {
            List<Date> bestDates = repository.bestDate(id);
            return bestDates != null ? bestDates : Collections.emptyList();
        } catch (Exception e) {
            System.err.println("Error in bestDate for sondageId " + id + ": " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public List<Date> maybeBestDate(Long id) {
        Sondage sondage = sondageService.getById(id);
        if (sondage == null || Boolean.TRUE.equals(sondage.getCloture())) {
            throw new EntityNotFoundException("Sondage with id " + id + " not found or closed");
        }
        try {
            List<Date> maybeBestDates = repository.maybeBestDate(id);
            return maybeBestDates != null ? maybeBestDates : Collections.emptyList();
        } catch (Exception e) {
            System.err.println("Error in maybeBestDate for sondageId " + id + ": " + e.getMessage());
            return Collections.emptyList();
        }
    }
}