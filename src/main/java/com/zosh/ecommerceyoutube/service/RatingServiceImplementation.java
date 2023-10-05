package com.zosh.ecommerceyoutube.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zosh.ecommerceyoutube.exception.ProductException;
import com.zosh.ecommerceyoutube.model.Product;
import com.zosh.ecommerceyoutube.model.Rating;
import com.zosh.ecommerceyoutube.model.User;
import com.zosh.ecommerceyoutube.repository.RatingRepository;
import com.zosh.ecommerceyoutube.request.RatingRequest;
@Service
public class RatingServiceImplementation implements RatingService {
	
	@Autowired
	private RatingRepository ratingRepository;
	
	@Autowired
	private ProductService productService;

	@Override
	public Rating creteRating(RatingRequest req, User user) throws ProductException {
		
		Product product =productService.findProductById(req.getProductId());
		
		Rating rating =new Rating();
		
		rating.setProduct(product);
		rating.setUser(user);
		rating.setRating(req.getRating());
		rating.setCreatedAt(LocalDate.now());
		return ratingRepository.save(rating);
	}

	@Override
	public List<Rating> getProductRating(Long prodcutId) {
		 
		return ratingRepository.getAllProductRating(prodcutId);
	}

}
