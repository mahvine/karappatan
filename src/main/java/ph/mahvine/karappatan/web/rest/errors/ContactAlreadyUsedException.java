package ph.mahvine.karappatan.web.rest.errors;

public class ContactAlreadyUsedException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public ContactAlreadyUsedException() {
        super(ErrorConstants.EMAIL_ALREADY_USED_TYPE, "Email is already in use!", "userManagement", "emailexists");
    }
}
