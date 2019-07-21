package ph.mahvine.karappatan.web.rest;


import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ph.mahvine.karappatan.domain.Authority;
import ph.mahvine.karappatan.domain.User;
import ph.mahvine.karappatan.repository.UserRepository;
import ph.mahvine.karappatan.security.jwt.JWTFilter;
import ph.mahvine.karappatan.security.jwt.TokenProvider;
import ph.mahvine.karappatan.service.UserService;
import ph.mahvine.karappatan.service.dto.FacebookLoginDTO;
import ph.mahvine.karappatan.service.dto.GoogleLoginDTO;
import ph.mahvine.karappatan.web.rest.UserJWTController.JWTToken;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
public class GoogleAccountResource {

    private final Logger log = LoggerFactory.getLogger(GoogleAccountResource.class);

    private final UserRepository userRepository;

    private final UserService userService;

    private final TokenProvider tokenProvider;

    public GoogleAccountResource(UserRepository userRepository, UserService userService, TokenProvider tokenProvider) {
    	this.tokenProvider = tokenProvider;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    /**
     * POST  /googleAuthenticate : login via google id/ email.
     */
    @PostMapping("/googleAuthenticate")
    public ResponseEntity<JWTToken> registerAccount(@Valid @RequestBody GoogleLoginDTO googleLogin) {
    	Optional<User> optUser = userRepository.findOneByGoogleId(googleLogin.getGoogleId());
		User user = null;
    	if(optUser.isPresent()) {
    		user = optUser.get();
    	} else {
    		//Link email
    		Optional<User> optEmailUser = userRepository.findOneByEmailIgnoreCase(googleLogin.getEmail());
    		if(optEmailUser.isPresent()) {
    			user = optEmailUser.get();
    			userService.linkGoogleUser(googleLogin.getEmail(), googleLogin.getGoogleId());
    		} else {
        		//Register
    			user = userService.registerGoogleUser(googleLogin);
    		}
    	}
    	
		String authorities = user.getAuthorities().stream()
	            .map(Authority::getName)
	            .collect(Collectors.joining(","));
		Authentication authentication = new UsernamePasswordAuthenticationToken(user, null,
				AuthorityUtils.createAuthorityList(authorities));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = tokenProvider.createToken(authentication, true);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
    }

    /**
     * POST  /account/google : link google id
     */
    @PostMapping("/account/google")
    public void linkAccount(@Valid @RequestBody GoogleLoginDTO login) {
    	User user = userService.getUserWithAuthorities().get();
		userService.linkGoogleUser(user.getEmail(), login.getGoogleId());
    }

    /**
     * DELETE  /account/google : unlink google id
     */
    @DeleteMapping("/account/google")
    public void unlinkAccount() {
    	User user = userService.getUserWithAuthorities().get();
		userService.unlinkGoogleUser(user.getEmail());
    }

}
