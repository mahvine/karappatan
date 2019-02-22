package ph.mahvine.karappatan.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

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
    private LocalDate dateCreated;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("caseSummaries")
    private User user;

    @ManyToMany
    @JoinTable(name = "case_summary_answers",
               joinColumns = @JoinColumn(name = "case_summary_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "answers_id", referencedColumnName = "id"))
    private Set<Answer> answers = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("caseSummaries")
    private Module module;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public CaseSummary dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
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

    public Set<Answer> getAnswers() {
        return answers;
    }

    public CaseSummary answers(Set<Answer> answers) {
        this.answers = answers;
        return this;
    }

    public CaseSummary addAnswers(Answer answer) {
        this.answers.add(answer);
        answer.getCaseSummaries().add(this);
        return this;
    }

    public CaseSummary removeAnswers(Answer answer) {
        this.answers.remove(answer);
        answer.getCaseSummaries().remove(this);
        return this;
    }

    public void setAnswers(Set<Answer> answers) {
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
}
