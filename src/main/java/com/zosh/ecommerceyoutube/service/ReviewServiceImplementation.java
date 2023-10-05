package com.zosh.ecommerceyoutube.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zosh.ecommerceyoutube.exception.ProductException;
import com.zosh.ecommerceyoutube.model.Product;
import com.zosh.ecommerceyoutube.model.Review;
import com.zosh.ecommerceyoutube.model.User;
import com.zosh.ecommerceyoutube.repository.ProductRepository;
import com.zosh.ecommerceyoutube.repository.ReviewRepository;
import com.zosh.ecommerceyoutube.request.ReviewRequest;

@Service
public class ReviewServiceImplementation implements ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductService productService;
	
	@Override
	public Review creteReview(ReviewRequest req, User user) throws ProductException {
		 Product product = productService.findProductById(req.getProductId());
		 Review review = new Review();
		 review.setProduct(product);
		 review.setUser(user);
		 review.setReview(req.getReview());
		 review.setCreatedAt(LocalDate.now());
		return reviewRepository.save(review);
	}

	@Override
	public List<Review> getProductReview(Long prodcutId) {
		//TODO Auto-generated method stub
		return reviewRepository.getAllProductReview(prodcutId);
	}

}
