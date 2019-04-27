package ph.mahvine.karappatan.service.dto;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the Annex entity.
 */
public class AnnexDTO implements Serializable {

    private Long id;

    private String content;
    
    private String identifier;

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

        AnnexDTO annexDTO = (AnnexDTO) o;
        if (annexDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), annexDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AnnexDTO{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", identifier='" + getIdentifier() + "'" +
            ", module=" + getModuleId() +
            ", module='" + getModuleTitle() + "'" +
            "}";
    }
}
