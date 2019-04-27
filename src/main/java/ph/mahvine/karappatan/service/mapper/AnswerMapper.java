package ph.mahvine.karappatan.service.mapper;

import ph.mahvine.karappatan.domain.*;
import ph.mahvine.karappatan.service.dto.AnswerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Answer and its DTO AnswerDTO.
 */
@Mapper(componentModel = "spring", uses = {AnnexMapper.class, RecommendationMapper.class, QuestionMapper.class})
public interface AnswerMapper extends EntityMapper<AnswerDTO, Answer> {

    @Mapping(source = "annex.id", target = "annexId")
    @Mapping(source = "annex.content", target = "annexContent")
    @Mapping(source = "recommendation.id", target = "recommendationId")
    @Mapping(source = "recommendation.content", target = "recommendationContent")
    @Mapping(source = "nextQuestion.id", target = "nextQuestionId")
    @Mapping(source = "nextQuestion.question", target = "nextQuestionQuestion")
    @Mapping(source = "question.id", target = "questionId")
    AnswerDTO toDto(Answer answer);

    @Mapping(source = "annexId", target = "annex")
    @Mapping(source = "recommendationId", target = "recommendation")
    @Mapping(source = "nextQuestionId", target = "nextQuestion")
    @Mapping(source = "questionId", target = "question")
    Answer toEntity(AnswerDTO answerDTO);

    default Answer fromId(Long id) {
        if (id == null) {
            return null;
        }
        Answer answer = new Answer();
        answer.setId(id);
        return answer;
    }
}
