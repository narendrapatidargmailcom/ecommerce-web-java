package com.zosh.ecommerceyoutube.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zosh.ecommerceyoutube.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
   
	@Query("Select o From Order o Where o.user.id=:userId AND (o.orderStatus ='PLACED' OR o.orderStatus='CONFIRMED' OR o.orderStatus='SHIPPED' OR o.orderStatus='DELIVERED')")
	public List<Order> getUsersOrder(@Param("userId") Long userId);
}
