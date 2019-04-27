package ph.mahvine.karappatan.service.mapper;

import ph.mahvine.karappatan.domain.*;
import ph.mahvine.karappatan.service.dto.QuestionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Question and its DTO QuestionDTO.
 */
@Mapper(componentModel = "spring", uses = {ModuleMapper.class, AnswerMapper.class})
public interface QuestionMapper extends EntityMapper<QuestionDTO, Question> {

    @Mapping(source = "module.id", target = "moduleId")
    @Mapping(source = "module.title", target = "moduleTitle")
    QuestionDTO toDto(Question question);

    @Mapping(source = "moduleId", target = "module")
    Question toEntity(QuestionDTO questionDTO);

    default Question fromId(Long id) {
        if (id == null) {
            return null;
        }
        Question question = new Question();
        question.setId(id);
        return question;
    }
}
