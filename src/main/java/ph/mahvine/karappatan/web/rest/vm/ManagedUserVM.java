package ph.mahvine.karappatan.web.rest.vm;

import ph.mahvine.karappatan.service.dto.UserDTO;
import javax.validation.constraints.Size;

/**
 * View Model extending the UserDTO, which is meant to be used in the user management UI.
 */
public class ManagedUserVM extends UserDTO {

    public static final int PASSWORD_MIN_LENGTH = 4;

    public static final int PASSWORD_MAX_LENGTH = 100;

    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;
    
    private boolean generateSession;

    public ManagedUserVM() {
        // Empty constructor needed for Jackson.
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "ManagedUserVM{" +
        	"generateSession:"+generateSession+
            "} " + super.toString();
    }

	public boolean isGenerateSession() {
		return generateSession;
	}

	public void setGenerateSession(boolean generateSession) {
		this.generateSession = generateSession;
	}
    
}
