package com.zosh.ecommerceyoutube.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.zosh.ecommerceyoutube.exception.ProductException;
import com.zosh.ecommerceyoutube.model.Product;
import com.zosh.ecommerceyoutube.request.CreateProductRequest;

public interface ProductService {
	
   public Product createProduct(CreateProductRequest  req);
   
   public String deleteProduct(Long productId) throws ProductException;
   
   public Product updateProduct(Long productId,Product product)throws ProductException;
   
   public Product findProductById(Long productId) throws ProductException;
   
   public List<Product> findProductByCategory(String category);
   
   public Page<Product> findAllProducts(String category,List<String> color,List<String> sizes,
   Integer minPrice,Integer maxPrice,Integer minDiscount,Integer maxDiscount,String sort,
   String stock,Integer pageNumber,Integer pageSize);
   
   public List<Product> findAll();
   
}
