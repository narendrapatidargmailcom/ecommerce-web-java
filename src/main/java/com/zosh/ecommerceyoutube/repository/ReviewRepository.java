package com.zosh.ecommerceyoutube.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zosh.ecommerceyoutube.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
  
	@Query("Select r from Review r Where r.product.id=:productid")
	List<Review> getAllProductReview(@Param("productid") Long productid);

}
