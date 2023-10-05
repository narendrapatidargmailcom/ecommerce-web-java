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
import com.zosh.ecommerceyoutube.model.Rating;
import com.zosh.ecommerceyoutube.model.Review;
import com.zosh.ecommerceyoutube.model.User;
import com.zosh.ecommerceyoutube.request.RatingRequest;
import com.zosh.ecommerceyoutube.request.ReviewRequest;
import com.zosh.ecommerceyoutube.service.RatingService;
import com.zosh.ecommerceyoutube.service.ReviewService;
import com.zosh.ecommerceyoutube.service.UserService;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
	@Autowired
    private UserService userService;
	
	@Autowired
	private ReviewService reviewService;
	
	@PostMapping("/create")
	public ResponseEntity<Review> createReview(@RequestBody ReviewRequest reviewRequest ,@RequestHeader("Authorization") String jwt) throws UserException, ProductException{
		  User user = userService.findUserProfileByJwt(jwt);
	   	  Review review = reviewService.creteReview(reviewRequest,user);
	      return new ResponseEntity<Review>(review ,HttpStatus.CREATED);
	}
	
	@GetMapping("/product/{productId}")
	public ResponseEntity<List<Review>> getProductReview(@PathVariable Long productId ,@RequestHeader("Authorization") String jwt) throws UserException, ProductException{
	   	  List<Review> reviews = reviewService.getProductReview(productId);
	      return new ResponseEntity<List<Review>>(reviews ,HttpStatus.CREATED);
	}
}
