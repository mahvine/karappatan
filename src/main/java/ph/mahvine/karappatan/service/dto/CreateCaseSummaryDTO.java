package ph.mahvine.karappatan.service.dto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A DTO for the CaseSummary entity.
 */
public class CreateCaseSummaryDTO implements Serializable {

    private List<Long> answerIds = new ArrayList<>();

	public List<Long> getAnswerIds() {
		return answerIds;
	}

	public void setAnswerIds(List<Long> answerIds) {
		this.answerIds = answerIds;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((answerIds == null) ? 0 : answerIds.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CreateCaseSummaryDTO other = (CreateCaseSummaryDTO) obj;
		if (answerIds == null) {
			if (other.answerIds != null)
				return false;
		} else if (!answerIds.equals(other.answerIds))
			return false;
		return true;
	}
    
    

}
