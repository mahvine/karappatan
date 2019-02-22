package ph.mahvine.karappatan.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the CaseSummary entity.
 */
public class CaseSummaryDTO implements Serializable {

    private Long id;

    private LocalDate dateCreated;


    private Long userId;

    private String userLogin;

//    private Set<Long> answers = new HashSet<>();

    private Long moduleId;

    private String moduleTitle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

//    public Set<Long> getAnswers() {
//        return answers;
//    }
//
//    public void setAnswers(Set<Long> answers) {
//        this.answers = answers;
//    }

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

        CaseSummaryDTO caseSummaryDTO = (CaseSummaryDTO) o;
        if (caseSummaryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), caseSummaryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CaseSummaryDTO{" +
            "id=" + getId() +
            ", dateCreated='" + getDateCreated() + "'" +
            ", user=" + getUserId() +
            ", user='" + getUserLogin() + "'" +
            ", module=" + getModuleId() +
            ", module='" + getModuleTitle() + "'" +
            "}";
    }
}
