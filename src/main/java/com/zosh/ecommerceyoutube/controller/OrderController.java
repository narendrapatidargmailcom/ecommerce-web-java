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

import com.zosh.ecommerceyoutube.exception.OrderException;
import com.zosh.ecommerceyoutube.exception.UserException;
import com.zosh.ecommerceyoutube.model.Address;
import com.zosh.ecommerceyoutube.model.Order;
import com.zosh.ecommerceyoutube.model.User;
import com.zosh.ecommerceyoutube.service.OrderService;
import com.zosh.ecommerceyoutube.service.UserService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
  
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/")
	public ResponseEntity<Order> createOrder(@RequestBody Address shippingAddress ,@RequestHeader("Authorization") String jwt) throws UserException{
		  User user = userService.findUserProfileByJwt(jwt);
	   	  Order order = orderService.createOrder(user,shippingAddress);
	   	  System.out.println("order--"+order);
	      return new ResponseEntity<Order>(order ,HttpStatus.CREATED);
	}
	
	@GetMapping("/user")
	public ResponseEntity<List<Order>> userOrderHistory(@RequestBody Address shippingAddress ,@RequestHeader("Authorization") String jwt) throws UserException{
		  User user = userService.findUserProfileByJwt(jwt);
	   	  List<Order> order = orderService.userOrderHistory(user.getId());
	      return new ResponseEntity<List<Order>>(order ,HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Order> findOrderById(@PathVariable("id") Long orderid ,@RequestHeader("Authorization") String jwt) throws UserException, OrderException{
		  User user = userService.findUserProfileByJwt(jwt);
	   	  Order order = orderService.findOrderById(orderid);
 	      return new ResponseEntity<Order>(order ,HttpStatus.ACCEPTED);
	}
}
