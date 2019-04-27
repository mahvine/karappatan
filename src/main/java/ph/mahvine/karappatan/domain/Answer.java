package ph.mahvine.karappatan.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Answer.
 */
@Entity
@Table(name = "krptn_answer")
public class Answer implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    
    @Column(name = "answer", nullable = false)
    private String answer;

    @Column(name = "instructions")
    private String instructions;

    @ManyToOne
    @JsonIgnoreProperties({"answers","module"})
    private Annex annex;

    @ManyToOne
    @JsonIgnoreProperties({"answers","module"})
    private Recommendation recommendation;

    @ManyToOne
    @JsonIgnoreProperties({"answers","module"})
    private Question nextQuestion;

    @ManyToOne
    @JsonIgnoreProperties({"answers","module"})
    private Question question;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public Answer answer(String answer) {
        this.answer = answer;
        return this;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getInstructions() {
        return instructions;
    }

    public Answer instructions(String instructions) {
        this.instructions = instructions;
        return this;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public Annex getAnnex() {
        return annex;
    }

    public Answer annex(Annex annex) {
        this.annex = annex;
        return this;
    }

    public void setAnnex(Annex annex) {
        this.annex = annex;
    }

    public Recommendation getRecommendation() {
        return recommendation;
    }

    public Answer recommendation(Recommendation recommendation) {
        this.recommendation = recommendation;
        return this;
    }

    public void setRecommendation(Recommendation recommendation) {
        this.recommendation = recommendation;
    }

    public Question getNextQuestion() {
        return nextQuestion;
    }

    public Answer nextQuestion(Question question) {
        this.nextQuestion = question;
        return this;
    }

    public void setNextQuestion(Question question) {
        this.nextQuestion = question;
    }

    public Question getQuestion() {
        return question;
    }

    public Answer question(Question question) {
        this.question = question;
        return this;
    }

    public void setQuestion(Question question) {
        this.question = question;
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
        Answer answer = (Answer) o;
        if (answer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), answer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Answer{" +
            "id=" + getId() +
            ", answer='" + getAnswer() + "'" +
            ", instructions='" + getInstructions() + "'" +
            "}";
    }
}
