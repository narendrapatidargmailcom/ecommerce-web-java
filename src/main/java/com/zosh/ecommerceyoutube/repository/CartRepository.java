package com.zosh.ecommerceyoutube.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.zosh.ecommerceyoutube.model.Cart;

public interface  CartRepository extends CrudRepository<Cart, Long> {

	@Query("Select c From Cart c Where c.user.id=:userId")
	public Cart findByUserId(@Param("userId") Long userId);

}
 