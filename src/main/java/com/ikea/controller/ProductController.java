package com.ikea.controller;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ikea.model.Product;
import com.ikea.service.ProductService;

@RestController
@RequestMapping("/ikea/api/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	// i - Get all products
	@GetMapping("")
	public ResponseEntity<List<Product>> get() {
		List<Product> products = null;

		products = productService.getProducts();

		if (products.isEmpty()) {
			return ResponseEntity.notFound().header("ERROR", "Inventory is empty.").build();
		}

		return ResponseEntity.ok(products);
	}

	// Get  product by id
	@GetMapping("/{id}")
	public ResponseEntity<Product> get(@PathVariable int id) {

		if (id < 0) {
			return ResponseEntity.badRequest().header("Error", "Invalid product id").build();
		}

		return ResponseEntity.ok( productService.getProduct(id));	
	}

	// ii Get filter product by category
	@GetMapping("/category/{category}")
	public ResponseEntity<List<String>> getByCategory(@PathVariable String category) {
		List<String> productsInfo = null;

		productsInfo = productService.getProducts(category);

		if (productsInfo.isEmpty()) {
			return ResponseEntity.notFound().header("ERROR", "No product exists for given category in inventory").build();
		}

		return ResponseEntity.ok(productsInfo);
	}

	// iii Creating new product
	@PostMapping("")
	public ResponseEntity<?> create(@RequestBody Product product) {
		if (product.getId() > 0) {
			return ResponseEntity.notFound().header("Error", "Product Id has to be empty").build();
		}
		
		productService.saveProduct(product);

		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	// iv Product Availability
	@GetMapping("/available/{code}")
	public ResponseEntity<String> getExistingProducts(@PathVariable String code) {
		String product = productService.getAvailableProducts(code);

		if (product.isEmpty()) {
			return ResponseEntity.notFound().header("ERROR", "No product exists in inventory").build();
		}

		return ResponseEntity.ok(product);
	}

	// v Product cost after tax.
	@GetMapping("/costaftertax/{category}")
	public ResponseEntity<List<String>> getProductAfterTax(@PathVariable String category) {
		List<String> result = productService.getProductAfterTax(category);

		if (result.isEmpty()) {
			return ResponseEntity.notFound().header("Error", "Product not found").build();
		}

		return ResponseEntity.ok(result); 
	}	

	// vi Product discount for most popular product.
	@GetMapping("/discount")
	public ResponseEntity<List<String>> getProductDiscount() {

		List<String> result = productService.getProductDiscount();

		if (result.isEmpty()) {
			return ResponseEntity.notFound().header("Error", "Product not found").build();
		}

		return ResponseEntity.ok(result);
	}

	// vii Update price of product
	@PutMapping("/id/{id}")
	public ResponseEntity<?> updateProduct(@PathVariable Integer id, @RequestParam double price){
		try {
			Product existProduct = productService.getProduct(id);

			if (existProduct != null) {
				existProduct.setPrice(price);

				productService.saveProduct(existProduct);
			}
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(NoSuchElementException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// viii Purge product with lowest rating
	@DeleteMapping("/purgebyrating")
	public ResponseEntity<String> purgeProduct(){	
		String resultMessage = productService.purgeProduct();

		return ResponseEntity.ok(resultMessage );
	}
}
