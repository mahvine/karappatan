package ph.mahvine.karappatan.service.dto;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Answer entity.
 */
@JsonInclude(Include.NON_NULL)
public class AnswerDTO implements Serializable {

    private Long id;

    
    private String answer;

    private String instructions;


    private Long annexId;

    private String annexContent;

    private Long recommendationId;

    private String recommendationContent;

    private Long nextQuestionId;

    private String nextQuestionQuestion;

    private Long questionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public Long getAnnexId() {
        return annexId;
    }

    public void setAnnexId(Long annexId) {
        this.annexId = annexId;
    }

    public String getAnnexContent() {
        return annexContent;
    }

    public void setAnnexContent(String annexContent) {
        this.annexContent = annexContent;
    }

    public Long getRecommendationId() {
        return recommendationId;
    }

    public void setRecommendationId(Long recommendationId) {
        this.recommendationId = recommendationId;
    }

    public String getRecommendationContent() {
        return recommendationContent;
    }

    public void setRecommendationContent(String recommendationContent) {
        this.recommendationContent = recommendationContent;
    }

    public Long getNextQuestionId() {
        return nextQuestionId;
    }

    public void setNextQuestionId(Long questionId) {
        this.nextQuestionId = questionId;
    }

    public String getNextQuestionQuestion() {
        return nextQuestionQuestion;
    }

    public void setNextQuestionQuestion(String questionQuestion) {
        this.nextQuestionQuestion = questionQuestion;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AnswerDTO answerDTO = (AnswerDTO) o;
        if (answerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), answerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AnswerDTO{" +
            "id=" + getId() +
            ", answer='" + getAnswer() + "'" +
            ", instructions='" + getInstructions() + "'" +
            ", annex=" + getAnnexId() +
            ", annex='" + getAnnexContent() + "'" +
            ", recommendation=" + getRecommendationId() +
            ", recommendation='" + getRecommendationContent() + "'" +
            ", nextQuestion=" + getNextQuestionId() +
            ", nextQuestion='" + getNextQuestionQuestion() + "'" +
            ", question=" + getQuestionId() +
            "}";
    }
}
