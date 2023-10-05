package com.zosh.ecommerceyoutube.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zosh.ecommerceyoutube.exception.ProductException;
import com.zosh.ecommerceyoutube.model.Cart;
import com.zosh.ecommerceyoutube.model.CartItem;
import com.zosh.ecommerceyoutube.model.Product;
import com.zosh.ecommerceyoutube.model.User;
import com.zosh.ecommerceyoutube.repository.CartRepository;
import com.zosh.ecommerceyoutube.request.AddItemRequest;
@Service
public class CartServiceImplementation implements CartService {
	
	@Autowired
	private CartRepository cartRepository;
	
	 @Autowired
	private CartItemService cartItemService; 
	
	@Autowired
	private ProductService  productService; 
	
	@Override
	public Cart createCart(User user) {
          Cart cart = new Cart();
          cart.setUser(user);
		return cartRepository.save(cart);
	}

	@Override
	public String addCartItem(Long userId, AddItemRequest req) throws ProductException {
		
		Cart cart=cartRepository.findByUserId(userId);
		Product product = productService.findProductById(req.getProductId());
		CartItem isPresent = cartItemService.isCartItemExist(cart, product, req.getSize(), userId);
		 if (isPresent==null) {
			CartItem cartItem = new CartItem();
			cartItem.setProduct(product);
			cartItem.setQuantity(req.getQuantity());
			cartItem.setCart(cart);
			cartItem.setUserId(userId); 	  	
			cartItem.setSize(req.getSize());
			int price = req.getQuantity()*product.getDiscountedPrice();
			cartItem.setPrice(price); 	
			
			CartItem CareatedCartItem = cartItemService.createCartItem(cartItem);	
			cart.getCartItems().add(CareatedCartItem);
		}
		return "item added to cart";
	}

	@Override
	public Cart findUserCart(Long userId) {
		Cart cart = cartRepository.findByUserId(userId);
		int totalPrice=0;
		int totalDiscountedPrice=0;
		int totalItem=0;
		
		for(CartItem cartItem:cart.getCartItems()) {
			
			totalPrice=totalPrice+cartItem.getPrice();
			totalDiscountedPrice=totalDiscountedPrice+cartItem.getDiscountedprice();
			totalItem = totalItem+ cartItem.getQuantity();		
		}
		
		cart.setTotalItem(totalItem);
		cart.setTotalDiscountedPrice(totalDiscountedPrice);
		cart.setTotalPrice(totalPrice);
		cart.setDiscount(totalPrice-totalDiscountedPrice);
		return cartRepository.save(cart);
	}

}
