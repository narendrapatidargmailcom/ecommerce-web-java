package com.zosh.ecommerceyoutube.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zosh.ecommerceyoutube.exception.OrderException;
import com.zosh.ecommerceyoutube.model.Order;
import com.zosh.ecommerceyoutube.response.AuthResponse;
import com.zosh.ecommerceyoutube.service.OrderService;

@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {

	@Autowired
	private OrderService orderService;
	
	@GetMapping("/")
	public ResponseEntity<List<Order>> getAllorder(){
	 	 List<Order> orders= orderService.getAllOrders();
		 return new ResponseEntity<List<Order>>(orders,HttpStatus.OK);
	}
	
	@PutMapping("/{orderId}/confirmed")
	public ResponseEntity<Order> confirmedOrderHandler(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException{
        Order order = orderService.confirmOrder(orderId);
	    return new ResponseEntity<Order>(order ,HttpStatus.OK);
		
	}
	@PutMapping("/{orderId}/ship")
	public ResponseEntity<Order> shippedOrder(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException{
        Order order = orderService.shippedOrder(orderId);
	    return new ResponseEntity<Order>(order ,HttpStatus.OK);
		
	}
	@PutMapping("/{orderId}/deliver")
	public ResponseEntity<Order> deliveredOrder(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException{
        Order order = orderService.deliveredOrder(orderId);
	    return new ResponseEntity<Order>(order ,HttpStatus.OK);
		
	}
	@PutMapping("/{orderId}/cancel")
	public ResponseEntity<Order> cancledOrder(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException{
        Order order = orderService.canceledOrder(orderId);
	    return new ResponseEntity<Order>(order ,HttpStatus.OK);
		
	}
	
	@DeleteMapping("/{orderId}/delete")
	public ResponseEntity<AuthResponse> deleteOrder(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException{
         orderService.deleteOrder(orderId);
         AuthResponse authResponse =new AuthResponse();
         authResponse.setMessage("Order deleted Sucessfully");
	    return new ResponseEntity<AuthResponse>(HttpStatus.OK);
		
	}
	
}
