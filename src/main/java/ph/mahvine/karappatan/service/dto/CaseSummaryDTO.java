package ph.mahvine.karappatan.service.dto;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ph.mahvine.karappatan.domain.Answer;

/**
 * A DTO for the CaseSummary entity.
 */
@JsonInclude(Include.NON_NULL)
public class CaseSummaryDTO implements Serializable {

    private Long id;

    private Instant dateCreated;

    private Long userId;

    private String userLogin;
    
    private UserDTO user;
    
    private List<Long> answerIds = new ArrayList<>();

    private Set<Answer> answers = new HashSet<>();

    private Long moduleId;

    private String moduleTitle;
    
    private String acceptedByLogin;
    
    private String details;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Instant dateCreated) {
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

    public Set<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<Answer> answers) {
        this.answers = answers;
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

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public List<Long> getAnswerIds() {
		return answerIds;
	}

	public void setAnswerIds(List<Long> answerIds) {
		this.answerIds = answerIds;
	}

	public String getAcceptedByLogin() {
		return acceptedByLogin;
	}

	public void setAcceptedByLogin(String acceptedByLogin) {
		this.acceptedByLogin = acceptedByLogin;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
}
