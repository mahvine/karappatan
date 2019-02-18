package ph.mahvine.karappatan.domain;



import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Module.
 */
@Entity
@Table(name = "krptn_module")
public class Module implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "details")
    private String details;

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name = "krptn_module_questions",
               joinColumns = @JoinColumn(name = "module_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "questions_id", referencedColumnName = "id"))
    private Set<Question> questions = new HashSet<>();

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name = "krptn_module_annexes",
               joinColumns = @JoinColumn(name = "module_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "annexes_id", referencedColumnName = "id"))
    private Set<Annex> annexes = new HashSet<>();

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name = "krptn_module_recommendations",
               joinColumns = @JoinColumn(name = "module_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "recommendations_id", referencedColumnName = "id"))
    private Set<Recommendation> recommendations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Module title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public Module details(String details) {
        this.details = details;
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public Module questions(Set<Question> questions) {
        this.questions = questions;
        return this;
    }

    public Module addQuestions(Question question) {
        this.questions.add(question);
        return this;
    }

    public Module removeQuestions(Question question) {
        this.questions.remove(question);
        return this;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }

    public Set<Annex> getAnnexes() {
        return annexes;
    }

    public Module annexes(Set<Annex> annexes) {
        this.annexes = annexes;
        return this;
    }

    public Module addAnnexes(Annex annex) {
        this.annexes.add(annex);
        return this;
    }

    public Module removeAnnexes(Annex annex) {
        this.annexes.remove(annex);
        return this;
    }

    public void setAnnexes(Set<Annex> annexes) {
        this.annexes = annexes;
    }

    public Set<Recommendation> getRecommendations() {
        return recommendations;
    }

    public Module recommendations(Set<Recommendation> recommendations) {
        this.recommendations = recommendations;
        return this;
    }

    public Module addRecommendations(Recommendation recommendation) {
        this.recommendations.add(recommendation);
        return this;
    }

    public Module removeRecommendations(Recommendation recommendation) {
        this.recommendations.remove(recommendation);
        return this;
    }

    public void setRecommendations(Set<Recommendation> recommendations) {
        this.recommendations = recommendations;
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
        Module module = (Module) o;
        if (module.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), module.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Module{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", details='" + getDetails() + "'" +
            "}";
    }
}
