package ph.mahvine.karappatan.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Objects;

/**
 * A Module.
 * 
 * Custom code in DTO and Mapper
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

    @OneToMany(mappedBy = "module", fetch=FetchType.EAGER)
    @OrderBy("id ASC")
    private List<Question> questions = new ArrayList<>();
    @OneToMany(mappedBy = "module", fetch=FetchType.EAGER)
    private List<Annex> annexes = new ArrayList<>();
    @OneToMany(mappedBy = "module", fetch=FetchType.EAGER)
    private List<Recommendation> recommendations = new ArrayList<>();
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

    public List<Question> getQuestions() {
        return questions;
    }

    public Module questions(List<Question> questions) {
        this.questions = questions;
        return this;
    }

    public Module addQuestions(Question question) {
        this.questions.add(question);
        question.setModule(this);
        return this;
    }

    public Module removeQuestions(Question question) {
        this.questions.remove(question);
        question.setModule(null);
        return this;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<Annex> getAnnexes() {
        return annexes;
    }

    public Module annexes(List<Annex> annexes) {
        this.annexes = annexes;
        return this;
    }

    public Module addAnnexes(Annex annex) {
        this.annexes.add(annex);
        annex.setModule(this);
        return this;
    }

    public Module removeAnnexes(Annex annex) {
        this.annexes.remove(annex);
        annex.setModule(null);
        return this;
    }

    public void setAnnexes(List<Annex> annexes) {
        this.annexes = annexes;
    }

    public List<Recommendation> getRecommendations() {
        return recommendations;
    }

    public Module recommendations(List<Recommendation> recommendations) {
        this.recommendations = recommendations;
        return this;
    }

    public Module addRecommendations(Recommendation recommendation) {
        this.recommendations.add(recommendation);
        recommendation.setModule(this);
        return this;
    }

    public Module removeRecommendations(Recommendation recommendation) {
        this.recommendations.remove(recommendation);
        recommendation.setModule(null);
        return this;
    }

    public void setRecommendations(List<Recommendation> recommendations) {
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
