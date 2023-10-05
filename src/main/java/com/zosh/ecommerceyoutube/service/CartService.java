  package com.zosh.ecommerceyoutube.service;

import com.zosh.ecommerceyoutube.exception.ProductException;
import com.zosh.ecommerceyoutube.model.Cart;
import com.zosh.ecommerceyoutube.model.User;
import com.zosh.ecommerceyoutube.request.AddItemRequest;

public interface CartService {
	
 public Cart createCart(User user);
   
 public String addCartItem(Long userId,AddItemRequest req) throws ProductException; 
 
 public Cart findUserCart(Long userId );
 
}
