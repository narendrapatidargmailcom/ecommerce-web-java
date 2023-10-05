package com.zosh.ecommerceyoutube.service;

import org.springframework.stereotype.Service;

import com.zosh.ecommerceyoutube.exception.UserException;
import com.zosh.ecommerceyoutube.model.User;

@Service
public interface UserService {
	
  public User findUserById(Long id) throws UserException;
  
  public User findUserProfileByJwt(String jwt) throws UserException;
}
