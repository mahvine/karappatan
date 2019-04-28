package ph.mahvine.karappatan.service.mapper;

import ph.mahvine.karappatan.domain.*;
import ph.mahvine.karappatan.repository.AnswerRepository;
import ph.mahvine.karappatan.repository.ModuleRepository;
import ph.mahvine.karappatan.service.dto.CaseSummaryDTO;
import ph.mahvine.karappatan.service.dto.UserDTO;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Mapper for the entity CaseSummary and its DTO CaseSummaryDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public abstract class CaseSummaryMapper implements EntityMapper<CaseSummaryDTO, CaseSummary> {

	@Autowired
	ModuleRepository moduleRepository;
	

	@Autowired
	AnswerRepository answerRepository;
	
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "module.id", target = "moduleId")
    @Mapping(source = "module.title", target = "moduleTitle")
    @Mapping(source = "user", target = "user")
    @Mapping(source = "answers", target = "answerIds")
    @Mapping(source = "acceptedBy.login", target = "acceptedByLogin")
    public abstract CaseSummaryDTO toDto(CaseSummary caseSummary);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "moduleId", target = "module")
    @Mapping(source = "answerIds", target = "answers")
    public abstract CaseSummary toEntity(CaseSummaryDTO caseSummaryDTO);

    public CaseSummary fromId(Long id) {
        if (id == null) {
            return null;
        }
        CaseSummary caseSummary = new CaseSummary();
        caseSummary.setId(id);
        return caseSummary;
    }

    public Module moduleFromId(Long id) {
    	if (id == null) {
    		return null;
    	}
    	return moduleRepository.getOne(id);
    }

    public Set<Long> answersToIds(Set<Answer> answers) {
    	return answers.stream().map(Answer::getId).collect(Collectors.toSet());
    }
    

    public Set<Answer> answersFromIds(Set<Long> answerIds) {
    	if(answerIds == null) {
    		return null;
    	}
    	return new HashSet<>(answerRepository.findAllById(answerIds));
    }
    
    
}
