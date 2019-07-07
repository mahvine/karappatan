package ph.mahvine.karappatan.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Annex.
 */
@Entity
@Table(name = "krptn_annex")
public class Annex implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "content")
    private String content;

    
    @Column(name = "identifier", unique = true)
    private String identifier;

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name = "krptn_annex_next_questions",
               joinColumns = @JoinColumn(name = "annex_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "next_questions_id", referencedColumnName = "id"))
    @JsonIgnoreProperties("module")
    private Set<Question> nextQuestions = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("annexes")
    private Module module;

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

    public Annex content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Annex identifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Set<Question> getNextQuestions() {
        return nextQuestions;
    }

    public Annex nextQuestions(Set<Question> questions) {
        this.nextQuestions = questions;
        return this;
    }

    public Annex addNextQuestions(Question question) {
        this.nextQuestions.add(question);
        return this;
    }

    public Annex removeNextQuestions(Question question) {
        this.nextQuestions.remove(question);
        return this;
    }

    public void setNextQuestions(Set<Question> questions) {
        this.nextQuestions = questions;
    }

    public Module getModule() {
        return module;
    }

    public Annex module(Module module) {
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
        Annex annex = (Annex) o;
        if (annex.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), annex.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Annex{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", identifier='" + getIdentifier() + "'" +
            "}";
    }
}
