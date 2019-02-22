package ph.mahvine.karappatan.service.mapper;

import ph.mahvine.karappatan.domain.*;
import ph.mahvine.karappatan.service.dto.CaseSummaryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CaseSummary and its DTO CaseSummaryDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, AnswerMapper.class, ModuleMapper.class})
public interface CaseSummaryMapper extends EntityMapper<CaseSummaryDTO, CaseSummary> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "module.id", target = "moduleId")
    @Mapping(source = "module.title", target = "moduleTitle")
    CaseSummaryDTO toDto(CaseSummary caseSummary);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "moduleId", target = "module")
    CaseSummary toEntity(CaseSummaryDTO caseSummaryDTO);

    default CaseSummary fromId(Long id) {
        if (id == null) {
            return null;
        }
        CaseSummary caseSummary = new CaseSummary();
        caseSummary.setId(id);
        return caseSummary;
    }
}
