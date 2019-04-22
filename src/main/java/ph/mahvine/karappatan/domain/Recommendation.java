package ph.mahvine.karappatan.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Objects;

/**
 * A Recommendation.
 */
@Entity
@Table(name = "krptn_recommendation")
public class Recommendation implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "content")
    private String content;

    @NotNull
    @Column(name = "identifier", nullable = false, unique = true)
    private String identifier;

    @ManyToOne(fetch=FetchType.EAGER)
    @JsonIgnoreProperties("recommendations")
    private Recommendation nextRecommendation;

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name = "krptn_recommendation_next_questions",
               joinColumns = @JoinColumn(name = "recommendation_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "next_questions_id", referencedColumnName = "id"))
    private List<Question> nextQuestions = new ArrayList<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public Recommendation content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Recommendation identifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Recommendation getNextRecommendation() {
        return nextRecommendation;
    }

    public Recommendation nextRecommendation(Recommendation recommendation) {
        this.nextRecommendation = recommendation;
        return this;
    }

    public void setNextRecommendation(Recommendation recommendation) {
        this.nextRecommendation = recommendation;
    }

    public List<Question> getNextQuestions() {
        return nextQuestions;
    }

    public Recommendation nextQuestions(List<Question> questions) {
        this.nextQuestions = questions;
        return this;
    }

    public Recommendation addNextQuestions(Question question) {
        this.nextQuestions.add(question);
        return this;
    }

    public Recommendation removeNextQuestions(Question question) {
        this.nextQuestions.remove(question);
        return this;
    }

    public void setNextQuestions(List<Question> questions) {
        this.nextQuestions = questions;
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
        Recommendation recommendation = (Recommendation) o;
        if (recommendation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), recommendation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Recommendation{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", identifier='" + getIdentifier() + "'" +
            "}";
    }
}
