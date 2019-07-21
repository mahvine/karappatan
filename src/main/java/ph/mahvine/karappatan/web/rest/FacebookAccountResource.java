package ph.mahvine.karappatan.web.rest;


import ph.mahvine.karappatan.config.Constants;
import ph.mahvine.karappatan.domain.Authority;
import ph.mahvine.karappatan.domain.User;
import ph.mahvine.karappatan.repository.UserRepository;
import ph.mahvine.karappatan.security.SecurityUtils;
import ph.mahvine.karappatan.security.jwt.JWTFilter;
import ph.mahvine.karappatan.security.jwt.TokenProvider;
import ph.mahvine.karappatan.service.MailService;
import ph.mahvine.karappatan.service.UserService;
import ph.mahvine.karappatan.service.dto.FacebookLoginDTO;
import ph.mahvine.karappatan.service.dto.PasswordChangeDTO;
import ph.mahvine.karappatan.service.dto.UserDTO;
import ph.mahvine.karappatan.web.rest.UserJWTController.JWTToken;
import ph.mahvine.karappatan.web.rest.errors.*;
import ph.mahvine.karappatan.web.rest.vm.KeyAndPasswordVM;
import ph.mahvine.karappatan.web.rest.vm.ManagedUserVM;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
public class FacebookAccountResource {

    private final Logger log = LoggerFactory.getLogger(FacebookAccountResource.class);

    private final UserRepository userRepository;

    private final UserService userService;

    private final TokenProvider tokenProvider;

    public FacebookAccountResource(UserRepository userRepository, UserService userService, TokenProvider tokenProvider) {
    	this.tokenProvider = tokenProvider;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    /**
     * POST  /fbAuthenticate : login via facebook id.
     */
    @PostMapping("/fbAuthenticate")
    public ResponseEntity<JWTToken> registerAccount(@Valid @RequestBody FacebookLoginDTO fbLogin) {
    	Optional<User> optUser = userRepository.findOneByFbId(fbLogin.getFbId());
		User user = null;
    	if(optUser.isPresent()) {
    		user = optUser.get();
    	} else {
    		//Link email
    		Optional<User> optEmailUser = userRepository.findOneByEmailIgnoreCase(fbLogin.getEmail());
    		if(optEmailUser.isPresent()) {
    			user = optEmailUser.get();
    			userService.linkFbUser(fbLogin.getEmail(), fbLogin.getFbId());
    		} else {
        		//Register
    			user = userService.registerFbUser(fbLogin);
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
     * POST  /account/facebook : link facebook id
     */
    @PostMapping("/account/facebook")
    public void linkAccount(@Valid @RequestBody FacebookLoginDTO fbLogin) {
    	User user = userService.getUserWithAuthorities().get();
		userService.linkFbUser(user.getEmail(), fbLogin.getFbId());
    }

    /**
     * DELETE  /account/facebook : unlink facebook id
     */
    @DeleteMapping("/account/facebook")
    public void unlinkAccount() {
    	User user = userService.getUserWithAuthorities().get();
		userService.unlinkFbUser(user.getEmail());
    }

}
