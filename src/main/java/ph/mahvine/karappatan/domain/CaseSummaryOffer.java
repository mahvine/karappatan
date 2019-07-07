package ph.mahvine.karappatan.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import ph.mahvine.karappatan.domain.enumeration.OfferStatus;

/**
 * A CaseSummaryOffer.
 */
@Entity
@Table(name = "case_summary_offer")
public class CaseSummaryOffer implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "date_created")
    private Instant dateCreated;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OfferStatus status;

    @ManyToOne
    @JsonIgnoreProperties({"user", "module", "answers"})
    private CaseSummary caseSummary;

    @ManyToOne
    @JsonIgnoreProperties("caseSummaryOffers")
    private User lawyer;

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

    public CaseSummaryOffer dateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    public OfferStatus getStatus() {
        return status;
    }

    public CaseSummaryOffer status(OfferStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(OfferStatus status) {
        this.status = status;
    }

    public CaseSummary getCaseSummary() {
        return caseSummary;
    }

    public CaseSummaryOffer caseSummary(CaseSummary caseSummary) {
        this.caseSummary = caseSummary;
        return this;
    }

    public void setCaseSummary(CaseSummary caseSummary) {
        this.caseSummary = caseSummary;
    }

    public User getLawyer() {
        return lawyer;
    }

    public CaseSummaryOffer lawyer(User user) {
        this.lawyer = user;
        return this;
    }

    public void setLawyer(User user) {
        this.lawyer = user;
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
        CaseSummaryOffer caseSummaryOffer = (CaseSummaryOffer) o;
        if (caseSummaryOffer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), caseSummaryOffer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CaseSummaryOffer{" +
            "id=" + getId() +
            ", dateCreated='" + getDateCreated() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
