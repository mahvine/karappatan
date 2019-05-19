package ph.mahvine.karappatan.domain;


import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * A CaseSummary.
 */
@Entity
@Table(name = "case_summary")
public class CaseSummary implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "date_created")
    private Instant dateCreated;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("caseSummaries")
    private User user;

    @ManyToMany
    @JoinTable(name = "case_summary_answers",
               joinColumns = @JoinColumn(name = "case_summary_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "answers_id", referencedColumnName = "id"))
    private List<Answer> answers = new ArrayList<>();
    
    @ManyToOne
    @JsonIgnoreProperties("caseSummaries")
    private Module module;


    @ManyToOne(optional = true)
    private User acceptedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateCreated() {
        return dateCreated;
    }

    public CaseSummary dateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    public User getUser() {
        return user;
    }

    public CaseSummary user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public CaseSummary answers(List<Answer> answers) {
        this.answers = answers;
        return this;
    }

    public CaseSummary addAnswers(Answer answer) {
        this.answers.add(answer);
        return this;
    }

    public CaseSummary removeAnswers(Answer answer) {
        this.answers.remove(answer);
        return this;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public Module getModule() {
        return module;
    }

    public CaseSummary module(Module module) {
        this.module = module;
        return this;
    }

    public void setModule(Module module) {
        this.module = module;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CaseSummary caseSummary = (CaseSummary) o;
        if (caseSummary.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), caseSummary.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CaseSummary{" +
            "id=" + getId() +
            ", dateCreated='" + getDateCreated() + "'" +
            "}";
    }

	public User getAcceptedBy() {
		return acceptedBy;
	}

	public void setAcceptedBy(User acceptedBy) {
		this.acceptedBy = acceptedBy;
	}
}
