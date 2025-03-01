package fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.services;

import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Commentaire;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.repositories.CommentaireRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentaireService {

    private final CommentaireRepository repository; // Mocker
    private final SondageService sondageService; // Mocker
    private final ParticipantService participantService; // Mocker

    // Tests Unitaires avec toutes les dépendances d'en haut mockées

    public CommentaireService(CommentaireRepository repository, SondageService s, ParticipantService p) {
        this.repository = repository;
        this.sondageService = s;
        this.participantService = p;
    }

    public List<Commentaire> getBySondageId(Long sondageId) {
        return repository.getAllBySondage(sondageId);
    }

    public Commentaire addCommantaire(Long idSondage, Long idParticipant, Commentaire commentaire) {
        commentaire.setSondage(sondageService.getById(idSondage));
        commentaire.setParticipant(participantService.getById(idParticipant));
        return repository.save(commentaire);
    }

    public Commentaire update(Long id, Commentaire commentaire) {
        Optional<Commentaire> existingCommentaireOpt = repository.findById(id);
        if (existingCommentaireOpt.isPresent()) {
            Commentaire existingCommentaire = existingCommentaireOpt.get();
            // Mettre à jour le texte du commentaire
            existingCommentaire.setCommentaire(commentaire.getCommentaire());
            // Mettre à jour les relations si elles sont fournies
            if (commentaire.getSondage() != null && commentaire.getSondage().getSondageId() != null) {
                existingCommentaire.setSondage(sondageService.getById(commentaire.getSondage().getSondageId()));
            }
            if (commentaire.getParticipant() != null && commentaire.getParticipant().getParticipantId() != null) {
                existingCommentaire.setParticipant(participantService.getById(commentaire.getParticipant().getParticipantId()));
            }
            return repository.save(existingCommentaire);
        }
        return null;
    }

    public int delete(Long id) {
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            return 1;
        }
        return 0;
    }
}
