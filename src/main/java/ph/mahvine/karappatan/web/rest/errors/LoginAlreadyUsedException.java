package ph.mahvine.karappatan.web.rest.errors;

public class LoginAlreadyUsedException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public LoginAlreadyUsedException() {
        super(ErrorConstants.CONTACT_ALREADY_USED_TYPE, "Contact already used!", "userManagement", "userexists");
    }
}
