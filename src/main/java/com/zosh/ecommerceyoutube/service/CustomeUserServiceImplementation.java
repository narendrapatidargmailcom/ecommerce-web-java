package com.zosh.ecommerceyoutube.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.zosh.ecommerceyoutube.EcommerceYoutubeApplication;
import com.zosh.ecommerceyoutube.model.User;
import com.zosh.ecommerceyoutube.repository.UserRepository;

@Service
public class CustomeUserServiceImplementation implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	private static final Logger loger = LogManager.getLogger(CustomeUserServiceImplementation.class.getName());


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		loger.info("Enter into the loadUserByUsername");

		User user = userRepository.findByEmail(username);
		
		if(user==null) {
			throw new UsernameNotFoundException("user not found with email--> "+username);
		}
		
		List<GrantedAuthority> authorities =new ArrayList<>();
		loger.info("Exit into the loadUserByUsername");

		return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
	}

}
