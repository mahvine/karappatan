package ph.mahvine.karappatan.service.mapper;

import ph.mahvine.karappatan.domain.*;
import ph.mahvine.karappatan.service.dto.RecommendationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Recommendation and its DTO RecommendationDTO.
 */
@Mapper(componentModel = "spring", uses = {QuestionMapper.class, ModuleMapper.class})
public interface RecommendationMapper extends EntityMapper<RecommendationDTO, Recommendation> {

    @Mapping(source = "nextRecommendation.id", target = "nextRecommendationId")
    @Mapping(source = "nextRecommendation.content", target = "nextRecommendationContent")
    @Mapping(source = "module.id", target = "moduleId")
    @Mapping(source = "module.title", target = "moduleTitle")
    RecommendationDTO toDto(Recommendation recommendation);

    @Mapping(source = "nextRecommendationId", target = "nextRecommendation")
    @Mapping(source = "moduleId", target = "module")
    Recommendation toEntity(RecommendationDTO recommendationDTO);

    default Recommendation fromId(Long id) {
        if (id == null) {
            return null;
        }
        Recommendation recommendation = new Recommendation();
        recommendation.setId(id);
        return recommendation;
    }
}
