package ph.mahvine.karappatan.service.mapper;

import ph.mahvine.karappatan.domain.*;
import ph.mahvine.karappatan.service.dto.ModuleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Module and its DTO ModuleDTO.
 */
@Mapper(componentModel = "spring", uses = {QuestionMapper.class, AnnexMapper.class, RecommendationMapper.class})
public interface ModuleMapper extends EntityMapper<ModuleDTO, Module> {



    default Module fromId(Long id) {
        if (id == null) {
            return null;
        }
        Module module = new Module();
        module.setId(id);
        return module;
    }
}
