package com.zosh.ecommerceyoutube.service;

import java.util.List;

import com.zosh.ecommerceyoutube.exception.ProductException;
import com.zosh.ecommerceyoutube.model.Rating;
import com.zosh.ecommerceyoutube.model.User;
import com.zosh.ecommerceyoutube.request.RatingRequest;

public interface RatingService {
	
  Rating  creteRating(RatingRequest req,User user) throws ProductException;
  List<Rating> getProductRating(Long prodcutId);

}
