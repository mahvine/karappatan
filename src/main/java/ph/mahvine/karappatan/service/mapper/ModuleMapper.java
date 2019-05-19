package ph.mahvine.karappatan.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ph.mahvine.karappatan.domain.Module;
import ph.mahvine.karappatan.service.dto.ModuleDTO;

/**
 * Mapper for the entity Module and its DTO ModuleDTO.
 */
@Mapper(componentModel = "spring", uses = {QuestionMapper.class, RecommendationMapper.class, AnnexMapper.class})
public interface ModuleMapper extends EntityMapper<ModuleDTO, Module> {


    @Mapping(source = "firstQuestion.id", target = "firstQuestionId")
    public abstract ModuleDTO toDto(Module module);
    
    @Mapping(source = "firstQuestionId", target = "firstQuestion")
    Module toEntity(ModuleDTO moduleDTO);

    default Module fromId(Long id) {
        if (id == null) {
            return null;
        }
        Module module = new Module();
        module.setId(id);
        return module;
    }

}
