package ph.mahvine.karappatan.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Recommendation entity.
 */
public class RecommendationDTO implements Serializable {

    private Long id;

    private String content;

    
    private String identifier;


    private Long nextRecommendationId;

    private String nextRecommendationContent;

    private Set<QuestionDTO> nextQuestions = new HashSet<>();

    private Long moduleId;

    private String moduleTitle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Long getNextRecommendationId() {
        return nextRecommendationId;
    }

    public void setNextRecommendationId(Long recommendationId) {
        this.nextRecommendationId = recommendationId;
    }

    public String getNextRecommendationContent() {
        return nextRecommendationContent;
    }

    public void setNextRecommendationContent(String recommendationContent) {
        this.nextRecommendationContent = recommendationContent;
    }

    public Set<QuestionDTO> getNextQuestions() {
        return nextQuestions;
    }

    public void setNextQuestions(Set<QuestionDTO> questions) {
        this.nextQuestions = questions;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleTitle() {
        return moduleTitle;
    }

    public void setModuleTitle(String moduleTitle) {
        this.moduleTitle = moduleTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RecommendationDTO recommendationDTO = (RecommendationDTO) o;
        if (recommendationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), recommendationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RecommendationDTO{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", identifier='" + getIdentifier() + "'" +
            ", nextRecommendation=" + getNextRecommendationId() +
            ", nextRecommendation='" + getNextRecommendationContent() + "'" +
            ", module=" + getModuleId() +
            ", module='" + getModuleTitle() + "'" +
            "}";
    }
}
