package fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.configurations;

import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.dtos.CommentaireDto;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.dtos.DateSondeeDto;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.dtos.ParticipantDto;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.dtos.SondageDto;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Commentaire;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.DateSondee;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Participant;
import fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models.Sondage;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        var mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        // Mapping CommentaireDto -> Commentaire (fusionné)
        mapper.createTypeMap(CommentaireDto.class, Commentaire.class).addMappings(m -> {
            m.skip(Commentaire::setParticipant); // Mapping existant
            m.map(CommentaireDto::getSondageId, (dest, v) -> dest.setSondage(new Sondage((Long) v))); // Nouveau mapping
            m.map(CommentaireDto::getParticipantId, (dest, v) -> dest.setParticipant(new Participant((Long) v))); // Nouveau mapping
        });

        // Mapping Commentaire -> CommentaireDto (fusionné)
        mapper.createTypeMap(Commentaire.class, CommentaireDto.class).addMappings(m -> {
            m.map(src -> src.getParticipant().getParticipantId(), CommentaireDto::setParticipantId); // Mapping existant corrigé
            m.map(src -> src.getSondage().getSondageId(), CommentaireDto::setSondageId); // Nouveau mapping
            // Supprimez cette ligne si elle est redondante : m.map(src -> src.getParticipant().getParticipantId(), CommentaireDto::setParticipantId);
        });

        // Autres mappings existants
        mapper.createTypeMap(DateSondeeDto.class, DateSondee.class).addMappings(
                m -> m.skip(DateSondee::setParticipant)
        );
        mapper.createTypeMap(DateSondee.class, DateSondeeDto.class).addMappings(
                m -> m.map(src -> src.getParticipant().getParticipantId(), DateSondeeDto::setParticipant)
        );
        mapper.createTypeMap(Participant.class, ParticipantDto.class).addMappings(
                m -> m.map(Participant::getParticipantId, ParticipantDto::setParticipantId)
        );
        mapper.createTypeMap(SondageDto.class, Sondage.class).addMappings(
                m -> m.skip(Sondage::setCreateBy)
        );
        mapper.createTypeMap(Sondage.class, SondageDto.class).addMappings(
                m -> m.map(src -> src.getCreateBy().getParticipantId(), SondageDto::setCreateBy)
        );
        // Dans une classe de configuration ou au démarrage
        mapper.typeMap(SondageDto.class, Sondage.class).addMappings(m -> {
            m.skip(Sondage::setCreateBy); // Ignorer createBy lors du mapping
        });
        mapper.typeMap(DateSondeeDto.class, DateSondee.class).addMappings(
                m -> m.map(DateSondeeDto::getChoix, DateSondee::setChoix)
        );
        mapper.typeMap(DateSondee.class, DateSondeeDto.class).addMappings(
                m -> m.map(DateSondee::getChoix, DateSondeeDto::setChoix)
        );
        mapper.typeMap(ParticipantDto.class, Participant.class).addMappings(
                m -> m.map(ParticipantDto::getParticipantId, Participant::setParticipantId)
        );
        mapper.typeMap(Participant.class, ParticipantDto.class).addMappings(
                m -> m.map(Participant::getParticipantId, ParticipantDto::setParticipantId)
        );

        return mapper;
    }
}
