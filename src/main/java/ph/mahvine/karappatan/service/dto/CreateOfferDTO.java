package ph.mahvine.karappatan.service.dto;

import java.io.Serializable;

public class CreateOfferDTO implements Serializable {

	private Long caseSummaryId;

	public Long getCaseSummaryId() {
		return caseSummaryId;
	}

	public void setCaseSummaryId(Long caseSummaryId) {
		this.caseSummaryId = caseSummaryId;
	}
	
}
