package ph.mahvine.karappatan.service.mapper;

import ph.mahvine.karappatan.domain.*;
import ph.mahvine.karappatan.service.dto.AnnexDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Annex and its DTO AnnexDTO.
 */
@Mapper(componentModel = "spring", uses = {QuestionMapper.class, ModuleMapper.class})
public interface AnnexMapper extends EntityMapper<AnnexDTO, Annex> {

    @Mapping(source = "module.id", target = "moduleId")
    @Mapping(source = "module.title", target = "moduleTitle")
    AnnexDTO toDto(Annex annex);

    @Mapping(source = "moduleId", target = "module")
    Annex toEntity(AnnexDTO annexDTO);

    default Annex fromId(Long id) {
        if (id == null) {
            return null;
        }
        Annex annex = new Annex();
        annex.setId(id);
        return annex;
    }
}
