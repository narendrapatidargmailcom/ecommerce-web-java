package com.zosh.ecommerceyoutube.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zosh.ecommerceyoutube.exception.CartItemException;
import com.zosh.ecommerceyoutube.exception.UserException;
import com.zosh.ecommerceyoutube.response.ApiResponse;
import com.zosh.ecommerceyoutube.service.CartItemService;

@RestController
@RequestMapping("/api/cartItem")
public class CartItemController {
     
	@Autowired
     private CartItemService cartItemService;
	
	@DeleteMapping("/{userid}/{id}")
   public ResponseEntity<ApiResponse> removedCartItem(@PathVariable("userid") Long userid,@PathVariable("id") Long cartItemId) throws UserException, CartItemException{
		System.out.println("removedCartItem run");
	   cartItemService.reMovedCartItem(userid, cartItemId);
	   ApiResponse apiResponse =new ApiResponse();
	   apiResponse.setMessage("item deleted Successfully");
	   apiResponse.setStatus(true);
	return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.ACCEPTED);
	   
   }
}
