package com.zosh.ecommerceyoutube.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.zosh.ecommerceyoutube.model.OrderItem;
import com.zosh.ecommerceyoutube.repository.OrderItemRepository;

public class OrderItemServiceImplementation implements OrderItemService{

	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Override
	public OrderItem createOrderItem(OrderItem orderItem) {
		 
		return orderItemRepository.save(orderItem);
	}

}
