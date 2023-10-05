package com.zosh.ecommerceyoutube.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zosh.ecommerceyoutube.config.JwtProvider;
import com.zosh.ecommerceyoutube.exception.UserException;
import com.zosh.ecommerceyoutube.model.User;
import com.zosh.ecommerceyoutube.repository.UserRepository;

@Service
public class UserServiceImplementation implements UserService {
   
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtProvider jwtProvider;
	
	@Override
	public User findUserById(Long id) throws UserException {
		Optional<User> user =userRepository.findById(id);
		if(user.isPresent()) {
			return 	user.get();
		}else
		{
			throw new UserException("User not found with id : "+id);
		}
		
	}

	@Override
	public User findUserProfileByJwt(String jwt) throws UserException {
		
		String email=jwtProvider.getEmailFromToken(jwt);
		User user = userRepository.findByEmail(email);
		if(user==null) {
			throw new UserException("user not found with Email"+email);
		}
		return user;
	}

}
