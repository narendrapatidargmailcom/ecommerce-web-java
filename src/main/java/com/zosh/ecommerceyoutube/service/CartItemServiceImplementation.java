package com.zosh.ecommerceyoutube.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zosh.ecommerceyoutube.exception.CartItemException;
import com.zosh.ecommerceyoutube.exception.UserException;
import com.zosh.ecommerceyoutube.model.Cart;
import com.zosh.ecommerceyoutube.model.CartItem;
import com.zosh.ecommerceyoutube.model.Product;
import com.zosh.ecommerceyoutube.model.User;
import com.zosh.ecommerceyoutube.repository.CartItemRepository;
import com.zosh.ecommerceyoutube.repository.CartRepository;

@Service
public class CartItemServiceImplementation implements CartItemService{

	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CartRepository cartRepository;
	
	
	@Override
	public CartItem createCartItem(CartItem cartItem) {
		cartItem.setQuantity(1);
		cartItem.setPrice(cartItem.getProduct().getPrice()*cartItem.getQuantity());
		cartItem.setDiscountedprice(cartItem.getProduct().getDiscountedPrice()*cartItem.getQuantity());
		CartItem createdCartItem = cartItemRepository.save(cartItem);

		return createdCartItem;
	}

	@Override
	public CartItem updateCartItem(Long userId,Long id, CartItem cartItem) throws CartItemException, UserException {
		CartItem item = findCartItemById(id);
		 User user = userService.findUserById(item.getUserId());
		 if (user.getId().equals(userId)) {
			item.setQuantity(cartItem.getQuantity());
			cartItem.setPrice(item.getProduct().getPrice()*item.getQuantity());
			cartItem.setDiscountedprice(item.getProduct().getDiscountedPrice()*item.getQuantity());
		}
		return cartItemRepository.save(item);
	}

	@Override
	public CartItem isCartItemExist(Cart cart, Product product, String size, Long userid) {
		 CartItem cartItem =cartItemRepository.isCartItemExist(cart, product, size, userid);
		return cartItem;
	}

	@Override
	public void reMovedCartItem(Long userId, Long cartItemId) throws UserException, CartItemException {
		
		CartItem cartItem =findCartItemById(cartItemId);
		User user = userService.findUserById(cartItem.getUserId());
		User requser = userService.findUserById(userId);
		if(user.getId().equals(requser.getId())){
			cartItemRepository.deleteById(cartItemId);
		}else
		{
			throw new UserException("you can't throw another user Exception");
		}
		
	}

	@Override
	public CartItem findCartItemById(Long cartItemId) throws CartItemException {
            Optional<CartItem> OptionalcartItem  =cartItemRepository.findById(cartItemId);
            
            if(OptionalcartItem.isPresent()) {
            	return OptionalcartItem.get();
            }else {
            	throw new CartItemException("cart not found with id"+cartItemId);
            }
      	}

}
