package com.zosh.ecommerceyoutube.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.zosh.ecommerceyoutube.exception.ProductException;
import com.zosh.ecommerceyoutube.model.Category;
import com.zosh.ecommerceyoutube.model.Product;
import com.zosh.ecommerceyoutube.repository.CategoryRepository;
import com.zosh.ecommerceyoutube.repository.ProductRepository;
import com.zosh.ecommerceyoutube.request.CreateProductRequest;

@Service
public class ProductServiceImplementation implements ProductService {
    
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
//	@Autowired
//	private UserService userService;
	
	@Override
	public Product createProduct(CreateProductRequest req) {
		
 		Category topLevelCategory = categoryRepository.findByName(req.getTopLevelcategory());
 		System.out.println("topLevelCategory "+topLevelCategory);
 		if(topLevelCategory==null)
 		{
 			Category topLevel = new Category();
 			topLevel.setName(req.getTopLevelcategory());
 			topLevel.setLevel(1);
 			System.out.println(topLevel.getName());
 			topLevelCategory = categoryRepository.save(topLevel);
 		}
 		
 	   Category secondLevelCategory = categoryRepository.findByNameAndParent(req.getSecondLevelcategory(),topLevelCategory.getName());
		System.out.println("secondLevelCategory "+secondLevelCategory);

 	   if(secondLevelCategory==null)
		{
			Category secondLevel = new Category();
			secondLevel.setName(req.getSecondLevelcategory());
			secondLevel.setLevel(2);
			secondLevel.setParentCategory(topLevelCategory);
			secondLevelCategory = categoryRepository.save(secondLevel);
		}
 	   
 	  Category thirdLevelCategory = categoryRepository.findByNameAndParent(req.getThirdLevelcategory(),secondLevelCategory.getName());
	 System.out.println("thirdLevelCategory "+thirdLevelCategory);

	   if(thirdLevelCategory==null)
		{
			Category thirdLevel = new Category();
			thirdLevel.setName(req.getThirdLevelcategory());
			thirdLevel.setLevel(3);
			thirdLevel.setParentCategory(secondLevelCategory);
			thirdLevelCategory = categoryRepository.save(thirdLevel);
		}
 		
 		Product product = new Product();
 		product.setTitle(req.getTitle());
 		product.setDescription(req.getDescription());
 		product.setColor(req.getColor());
 		product.setDiscountedPrice(req.getDiscountedPrice());
 		product.setDiscountPersent(req.getDiscountPersent());
 		product.setImageUrl(req.getImageUrl());
 		product.setBrand(req.getBrand());
 		product.setPrice(req.getPrice());
 		product.setSizes(req.getSizes());
 		product.setQuantity(req.getQuantity());
 		product.setCategory(thirdLevelCategory);
 		product.setCreatedAt(LocalDateTime.now());
        
 		Product savedProduct = productRepository.save(product);
		return savedProduct;
	}

	@Override
	public String deleteProduct(Long productId) throws ProductException {
 		Product product = productRepository.findById(productId).get();
 		 product.getSizes().clear();
 		 productRepository.delete(product);
		return "Prodcut deleted Successfully";
	}

	@Override
	public Product updateProduct(Long productId, Product reqProduct) throws ProductException {
 		
		Product product = productRepository.findById(productId).get();
		
		if(reqProduct.getQuantity()!=0) {
			product.setQuantity(reqProduct.getQuantity());
		}
 		
		return productRepository.save(product);
	}

	@Override
	public Product findProductById(Long productId) throws ProductException {
        Optional<Product> product = productRepository.findById(productId);
        
        if(product.isPresent()) {
        	return product.get();
        }
        throw new ProductException("Product not found-->"+productId);
 	}

	@Override
	public List<Product> findProductByCategory(String category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Product> findAllProducts(String category, List<String> color, List<String> sizes, Integer minPrice,
			Integer maxPrice, Integer minDiscount, Integer maxDiscount, String sort, String stock, Integer pageNumber,
		Integer pageSize) {
		
		 Pageable pageable =PageRequest.of(pageNumber, pageSize);
		 List<Product> products = productRepository.filterProducts(category, minPrice, maxPrice, minDiscount, sort);
		 
		 if(!color.isEmpty()) {
			 products = products.stream().filter(p->color.stream().anyMatch(c->c.equalsIgnoreCase(p.getColor()))).collect(Collectors.toList()); 
		 }
		 
		 if(stock!=null) {
			 if(stock.equals("in_stock")) {
			 products = products.stream().filter(p-> p.getQuantity()>0).collect(Collectors.toList());
			 }else if(stock.equals("out_of_stock")) {
				 products = products.stream().filter(p-> p.getQuantity()<1).collect(Collectors.toList());
			 }
		 }
		 
		 int startIndex=(int) pageable.getOffset();
		 
		 int endIndex = Math.min(startIndex+pageable.getPageSize(), products.size());
		 
		 List<Product> pageContent =products.subList(startIndex, endIndex);
		 
		 Page<Product> filterProducts = new PageImpl<>(pageContent ,pageable,products.size());
		 
		 
		 
		 
		return filterProducts;
	}

	@Override
	public List<Product> findAll() {
	
		return productRepository.findAll();
	}

}
