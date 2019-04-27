package ph.mahvine.karappatan.service.dto;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Module entity.
 */
public class ModuleDTO implements Serializable {

    private Long id;

    @NotNull
    private String title;

    private String details;

    @JsonIgnoreProperties("module")
    private Set<QuestionDTO> questions = new HashSet<>();

    private Set<AnnexDTO> annexes = new HashSet<>();

    private Set<RecommendationDTO> recommendations = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Set<QuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<QuestionDTO> questions) {
        this.questions = questions;
    }

    public Set<AnnexDTO> getAnnexes() {
        return annexes;
    }

    public void setAnnexes(Set<AnnexDTO> annexes) {
        this.annexes = annexes;
    }

    public Set<RecommendationDTO> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(Set<RecommendationDTO> recommendations) {
        this.recommendations = recommendations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ModuleDTO moduleDTO = (ModuleDTO) o;
        if (moduleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), moduleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ModuleDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", details='" + getDetails() + "'" +
            "}";
    }
}
