package ph.mahvine.karappatan.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Karappatan.
 * <p>
 * Properties are configured in the application.yml file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {
	
	private boolean caseSummaryAcceptanceEnabled;

	public boolean isCaseSummaryAcceptanceEnabled() {
		return caseSummaryAcceptanceEnabled;
	}

	public void setCaseSummaryAcceptanceEnabled(boolean caseSummaryAcceptanceEnabled) {
		this.caseSummaryAcceptanceEnabled = caseSummaryAcceptanceEnabled;
	}
	
}
