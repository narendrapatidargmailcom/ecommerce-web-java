package com.zosh.ecommerceyoutube.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zosh.ecommerceyoutube.exception.ProductException;
import com.zosh.ecommerceyoutube.exception.UserException;
import com.zosh.ecommerceyoutube.model.Cart;
import com.zosh.ecommerceyoutube.model.User;
import com.zosh.ecommerceyoutube.request.AddItemRequest;
import com.zosh.ecommerceyoutube.response.ApiResponse;
import com.zosh.ecommerceyoutube.service.CartService;
import com.zosh.ecommerceyoutube.service.UserService;

@RestController
@RequestMapping("/api/cart")
public class CartController {
  
	@Autowired	
     private CartService cartService;
 
   
    @Autowired
     private UserService userService;
    
    @GetMapping("/")
   	public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt) throws UserException {
   	    User user = userService.findUserProfileByJwt(jwt);
   	    System.out.println(user.getEmail());
   	    Cart cart = cartService.findUserCart(user.getId());
    	return new ResponseEntity<Cart>(cart ,HttpStatus.OK);
   		
   	}
    
    @PutMapping("/add")
   	public ResponseEntity<ApiResponse> addItemToCart(@RequestBody AddItemRequest addItemRequest ,@RequestHeader("Authorization") String jwt) throws UserException, ProductException {
   	    User user = userService.findUserProfileByJwt(jwt);
   	    cartService.addCartItem(user.getId(),addItemRequest);
   	    ApiResponse apiResponse = new ApiResponse();
   	    apiResponse.setMessage("item added to cart");
   	    apiResponse.setStatus(true);
    	return new ResponseEntity<ApiResponse>(apiResponse ,HttpStatus.OK);
   		
   	}
}











