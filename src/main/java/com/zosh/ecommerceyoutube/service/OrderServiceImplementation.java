package com.zosh.ecommerceyoutube.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zosh.ecommerceyoutube.exception.OrderException;
import com.zosh.ecommerceyoutube.model.Address;
import com.zosh.ecommerceyoutube.model.Cart;
import com.zosh.ecommerceyoutube.model.Order;
import com.zosh.ecommerceyoutube.model.OrderItem;
import com.zosh.ecommerceyoutube.model.User;
import com.zosh.ecommerceyoutube.repository.AddressRepository;
import com.zosh.ecommerceyoutube.repository.CartRepository;
import com.zosh.ecommerceyoutube.repository.OrderItemRepository;
import com.zosh.ecommerceyoutube.repository.OrderRepository;
import com.zosh.ecommerceyoutube.repository.UserRepository;
@Service
public class OrderServiceImplementation implements OrderService {

	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private  ProductService productService;
	
	@Autowired
	private AddressRepository addressRepository; 
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Override
	public Order createOrder(User user, Address shippedAddress) {
		shippedAddress.setUser(user);
		 Address address=  addressRepository.save(shippedAddress);
		 user.getAddress().add(address);
		 userRepository.save(user);
		 Cart cart= cartService.findUserCart(user.getId());
		 List<OrderItem> orderItems = new ArrayList<>();
		 for(OrderItem item:orderItems) {
			 OrderItem orderItem =new OrderItem();
			 orderItem.setPrice(item.getPrice());
			 orderItem.setProduct(item.getProduct());
			 orderItem.setQuantity(item.getQuantity());
			 orderItem.setSize(item.getSize());
			 orderItem.setUserId(item.getUserId());
			 orderItem.setDiscountedPrice(item.getDiscountedPrice());
			 
			 OrderItem createdOrderItem = orderItemRepository.save(orderItem);
			 
			 orderItems.add(createdOrderItem);
		 }
		 
		 Order createdOrder = new Order();
		 createdOrder.setUser(user);
		 createdOrder.setOrderitem(orderItems);
		 createdOrder.setTotalPrice(cart.getTotalPrice());
		 createdOrder.setTotalDiscountedPrice(cart.getTotalDiscountedPrice());
		 createdOrder.setDiscount(cart.getDiscount());
		 createdOrder.setTotalItem(cart.getTotalItem());
		 createdOrder.setShippingAddress(address);
		 createdOrder.setOrderDate(LocalDateTime.now());
		 createdOrder.setOrderStatus("PENDIND");
		 createdOrder.getPaymentDetail().setPaymentStatus("PENDING");
		 createdOrder.setCreatedAt(LocalDateTime.now());
		 
		 Order savedOrder = orderRepository.save(createdOrder);
		 
		 for(OrderItem orderItem:orderItems) {
			 orderItem.setOrder(savedOrder);
			 orderItemRepository.save(orderItem);
		 }
		 
		 
		return savedOrder;
	}

	@Override
	public Order findOrderById(Long orderId) throws OrderException {
	     Optional<Order> order =orderRepository.findById(orderId);
	     if(order.isPresent())
	    	 return order.get();
	     
		 throw new OrderException("order not exist with id"+orderId);
	}

	@Override
	public List<Order> userOrderHistory(Long userId) {
     List<Order> orders = orderRepository.getUsersOrder(userId);
		return orders;
	}

	@Override
	public Order placedOrder(Long orderId) throws OrderException {
		Order order =findOrderById(orderId);
		order.setOrderStatus("PLACED");
		order.getPaymentDetail().setPaymentStatus("COMPLETED");;
		return order;
	}

	@Override
	public Order confirmOrder(Long orderId) throws OrderException {
		Order order =findOrderById(orderId);
		order.setOrderStatus("CONFIRMED");
		return order;
	}

	@Override
	public Order shippedOrder(Long orderId) throws OrderException {
		Order order =findOrderById(orderId);
		order.setOrderStatus("SHIPPED");
		return order;
	}

	@Override
	public Order deliveredOrder(Long orderId) throws OrderException {
		Order order =findOrderById(orderId);
		order.setOrderStatus("DELIVERED");
		return order;
	}

	@Override
	public Order canceledOrder(Long orderId) throws OrderException {
		Order order =findOrderById(orderId);
		order.setOrderStatus("CANCELED");
		return order;
	}

	@Override
	public List<Order> getAllOrders() {
		
		return orderRepository.findAll();
	}

	@Override
	public void deleteOrder(Long orderId) {
		Order order = null;
		try {
			order = findOrderById(orderId);
		} catch (OrderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		orderRepository.delete(order);
		return ;
		
	}

}
