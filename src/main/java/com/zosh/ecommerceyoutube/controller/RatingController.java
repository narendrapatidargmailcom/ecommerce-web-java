package com.zosh.ecommerceyoutube.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zosh.ecommerceyoutube.exception.ProductException;
import com.zosh.ecommerceyoutube.exception.UserException;
import com.zosh.ecommerceyoutube.model.Address;
import com.zosh.ecommerceyoutube.model.Order;
import com.zosh.ecommerceyoutube.model.Rating;
import com.zosh.ecommerceyoutube.model.User;
import com.zosh.ecommerceyoutube.request.RatingRequest;
import com.zosh.ecommerceyoutube.service.RatingService;
import com.zosh.ecommerceyoutube.service.UserService;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {
	
	@Autowired
    private UserService userService;
	
	@Autowired
	private RatingService ratingService;
	

	@PostMapping("/create")
	public ResponseEntity<Rating> createRatig(@RequestBody RatingRequest ratingRequest ,@RequestHeader("Authorization") String jwt) throws UserException, ProductException{
		  User user = userService.findUserProfileByJwt(jwt);
	   	  Rating rating = ratingService.creteRating(ratingRequest,user);
	      return new ResponseEntity<Rating>(rating ,HttpStatus.CREATED);
	}
	
	@GetMapping("/product/{productId}")
	public ResponseEntity<List<Rating>> getProductRatings(@PathVariable Long productId ,@RequestHeader("Authorization") String jwt) throws UserException, ProductException{
	   	  List<Rating> rating = ratingService.getProductRating(productId);
	      return new ResponseEntity<List<Rating>>(rating ,HttpStatus.CREATED);
	}
}
