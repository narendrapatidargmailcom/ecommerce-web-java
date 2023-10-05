package com.zosh.ecommerceyoutube.service;

import com.zosh.ecommerceyoutube.exception.CartItemException;
import com.zosh.ecommerceyoutube.exception.UserException;
import com.zosh.ecommerceyoutube.model.Cart;
import com.zosh.ecommerceyoutube.model.CartItem;
import com.zosh.ecommerceyoutube.model.Product;

public interface CartItemService {
	  
  public CartItem createCartItem(CartItem cartItem);
  
  public CartItem updateCartItem(Long cartItemId,Long id,CartItem cartItem)throws CartItemException,UserException;
  
  public CartItem isCartItemExist(Cart cart,Product product,String size,Long userid);
  
  public  void reMovedCartItem(Long userId,Long cartItem) throws UserException, CartItemException;
  
  public CartItem findCartItemById(Long cartItemId) throws CartItemException;
  
}
