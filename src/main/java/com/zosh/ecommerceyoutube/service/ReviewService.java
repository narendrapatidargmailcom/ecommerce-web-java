package com.zosh.ecommerceyoutube.service;

import java.util.List;

import com.zosh.ecommerceyoutube.exception.ProductException;
import com.zosh.ecommerceyoutube.model.Review;
import com.zosh.ecommerceyoutube.model.User;
import com.zosh.ecommerceyoutube.request.ReviewRequest;

public interface ReviewService  {
	   Review  creteReview(ReviewRequest req,User user) throws ProductException;
	   List<Review> getProductReview(Long prodcutId);
}
