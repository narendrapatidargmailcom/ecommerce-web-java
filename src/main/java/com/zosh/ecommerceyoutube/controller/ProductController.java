package com.zosh.ecommerceyoutube.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zosh.ecommerceyoutube.exception.ProductException;
import com.zosh.ecommerceyoutube.model.Product;
import com.zosh.ecommerceyoutube.service.ProductService;

@RequestMapping("/api")
@RestController
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping("products")
	public ResponseEntity<Page<Product>> findProductByCategory(@RequestParam String category,
			@RequestParam List<String> colors, @RequestParam List<String> sizes, @RequestParam Integer minPrice,
			@RequestParam Integer maxPrice, @RequestParam Integer minDiscount, @RequestParam Integer pageNumber,
			@RequestParam Integer pageSize, @RequestParam String sort, @RequestParam String stock) {

		Page<Product> products = productService.findAllProducts(category, colors, sizes, minPrice, maxPrice,
				minDiscount, minDiscount, sort, stock, pageNumber, pageSize);

		return new ResponseEntity<Page<Product>>(products, HttpStatus.ACCEPTED);

	}
	
	@GetMapping("products/id/{productId}")
	public ResponseEntity<Product> findProductById(@PathVariable Long productId) throws ProductException {
        Product product = productService.findProductById(productId);
		return new ResponseEntity<Product>(product, HttpStatus.ACCEPTED);

	}
	
//	@GetMapping("products/all")
//	public ResponseEntity<List<Product>> getAllProduct(@PathVariable Long productId) throws ProductException {
//        List<Product> product = productService.findAll();
//		return new ResponseEntity<List<Product>>(product, HttpStatus.ACCEPTED);
//
//	}
}
