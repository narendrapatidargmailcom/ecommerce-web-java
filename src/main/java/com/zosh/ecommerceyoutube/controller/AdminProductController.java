package com.zosh.ecommerceyoutube.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zosh.ecommerceyoutube.exception.ProductException;
import com.zosh.ecommerceyoutube.model.Product;
import com.zosh.ecommerceyoutube.request.CreateProductRequest;
import com.zosh.ecommerceyoutube.response.ApiResponse;
import com.zosh.ecommerceyoutube.service.ProductService;

@RestController
@RequestMapping("/api/admin/products")
public class AdminProductController {
	
	@Autowired
    private ProductService  productService;
	
   @PostMapping("/")
	public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest req) {
        Product  product = productService.createProduct(req);
	    return new ResponseEntity<Product>(product ,HttpStatus.CREATED);
		
	}
   
   @DeleteMapping("/{productId}/delete")
	public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productid) throws ProductException {
         productService.deleteProduct(productid);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("product deleted successfully");;
        apiResponse.setStatus(true);
	    return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.OK);	
		
	}
   
   @GetMapping("/all")
	public ResponseEntity<List<Product>> findAllProduct() {
       List<Product>  products = productService.findAll();
	    return new ResponseEntity<List<Product>>(products ,HttpStatus.OK);
		
	}
   
   @PutMapping("/all")
	public ResponseEntity<Product> updateProduct(@RequestBody Product product,@PathVariable Long productId) throws ProductException {
       Product products = productService.updateProduct(productId,product);
	    return new ResponseEntity<Product>(products ,HttpStatus.OK);
		
	}
   

}
