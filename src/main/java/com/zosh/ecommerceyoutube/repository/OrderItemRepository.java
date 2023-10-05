package com.zosh.ecommerceyoutube.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zosh.ecommerceyoutube.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
