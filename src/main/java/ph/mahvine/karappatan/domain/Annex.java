package ph.mahvine.karappatan.domain;



import javax.persistence.*;

import org.hibernate.annotations.Type;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Annex.
 */
@Entity
@Table(name = "krptn_annex")
public class Annex implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Lob
    @Column(name = "content")
    @Type(type = "org.hibernate.type.TextType")
    private String content;

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
            "}";
    }
}
