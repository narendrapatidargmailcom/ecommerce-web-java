package com.zosh.ecommerceyoutube.controller;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zosh.ecommerceyoutube.config.JwtProvider;
import com.zosh.ecommerceyoutube.config.JwtValidator;
import com.zosh.ecommerceyoutube.exception.UserException;
import com.zosh.ecommerceyoutube.model.Cart;
import com.zosh.ecommerceyoutube.model.User;
import com.zosh.ecommerceyoutube.repository.UserRepository;
import com.zosh.ecommerceyoutube.request.LoginRequest;
import com.zosh.ecommerceyoutube.response.AuthResponse;
import com.zosh.ecommerceyoutube.service.CartService;
import com.zosh.ecommerceyoutube.service.CustomeUserServiceImplementation;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtProvider jwtProvider;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private CustomeUserServiceImplementation customeUserServiceImplementation;
	
	private static final Logger loger = LogManager.getLogger(AuthController.class.getName());

	
	@PostMapping("/signup")
   public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException{
		loger.info("Enter into the createUserHandler");
	   String email = user.getEmail();
	   String password = user.getPassword();
	   String firstName = user.getFirstName();
	   String lastName = user.getLastName();
	   
	   User isUserExist = userRepository.findByEmail(email);
	   
	   if(isUserExist!=null) {
		    throw new UserException("Email is already used with another account");
	   }
	   
	   User createdUser = new User();
	   createdUser.setEmail(email);
	   createdUser.setPassword(passwordEncoder.encode(password));
	   createdUser.setFirstName(firstName);
	   createdUser.setLastName(lastName);
	   
	   User savedUser =  userRepository.save(createdUser);
	   Cart cart = cartService.createCart(savedUser);
	   
	   Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());
	   
	   SecurityContextHolder.getContext().setAuthentication(authentication);
	   
	   String token = jwtProvider.generateToken(authentication);
	   
	   AuthResponse authResponse =new AuthResponse();
	   authResponse.setJwt(token);
	   authResponse.setMessage("singn up successfully ");
	   
	   loger.info("Exit from the createUserHandler");
	   return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.CREATED);
   }
	
	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> loginUserHandler(@RequestBody LoginRequest loginRequest){
		   loger.info("Enter  into the createUserHandler");

		String username =loginRequest.getEmail();
		String password = loginRequest.getPassword();
		
		Authentication  authentication = authenticate(username,password);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtProvider.generateToken(authentication);
		   
		AuthResponse authResponse =new AuthResponse();
	    authResponse.setJwt(token);
		authResponse.setMessage("singn up successfully ");
		loger.info("Exit from the createUserHandler");
		   
		   return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.CREATED);
		
		
	}

	private Authentication authenticate(String username, String password) {
		loger.info("Enter into the authenticate");
		UserDetails userDetails =customeUserServiceImplementation.loadUserByUsername(username);
		
		if (userDetails==null) {
			throw new BadCredentialsException("invalid username....");
		}
		
		if (!(passwordEncoder.matches(password, userDetails.getPassword()))) {
		    throw new BadCredentialsException("invalid password....");	
		}
		loger.info("Exit from the authenticate");
		return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
	}
}
