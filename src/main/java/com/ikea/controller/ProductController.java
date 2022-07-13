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

	@GetMapping("")
	public ResponseEntity<List<Product>> get() {
		List<Product> products = null;

		products = productService.getProducts();

		if (products.isEmpty()) {
			return ResponseEntity.notFound().header("ERROR", "Inventory is empty.").build();
		}

		return ResponseEntity.ok(products);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Product> get(@PathVariable int id) {

		if (id < 0) {
			return ResponseEntity.badRequest().header("Error", "Invalid product id").build();
		}

		return ResponseEntity.ok( productService.getProduct(id));	
	}

	//Feature ii
	@GetMapping("/category/{category}")
	public ResponseEntity<List<String>> getByCategory(@PathVariable String category) {
		List<String> productsInfo = null;

		if (category == null || category.isBlank()) {
			ResponseEntity.badRequest().header("Error", "category value is null or empty.").build();
		}

		productsInfo = productService.getProducts(category);

		if (productsInfo.isEmpty()) {
			return ResponseEntity.notFound().header("ERROR", "No product exists for given category in inventory").build();
		}

		return ResponseEntity.ok(productsInfo);
	}

	@PostMapping("")
	public void create(@RequestBody Product product) {
		 productService.saveProduct(product);
		
	}

	//Feature iv
	@GetMapping("/avilable/{code}")
	public ResponseEntity<String> getExistingProducts(@PathVariable String code) {
		String product = productService.getExistingProducts(code);

		if (product.isEmpty()) {
			return ResponseEntity.notFound().header("ERROR", "No product exists in inventory").build();
		}

		return ResponseEntity.ok(product);
	}

	@GetMapping("/discount")
	public ResponseEntity<List<String>> getProductDiscount() {

		List<String> result = productService.getProductDiscount();

		if (result  == null) {
			return ResponseEntity.notFound().header("Error", "Product not found").build();
		}

		return ResponseEntity.ok(result);
	}


	@GetMapping("/costaftertax/{category}")
	public ResponseEntity<List<String>> getProductAfterTax(@PathVariable String category) {

		if (category.isBlank()) {
			return ResponseEntity.badRequest().header("Error", "Value of product code is empty").build();
		}

		List<String> result = productService.getProductAfterTax(category);

		if (result  == null) {
			return ResponseEntity.notFound().header("Error", "Product not found").build();
		}

		return ResponseEntity.ok(result); 
	}	
	
	@PutMapping("/id/{id}")
	public ResponseEntity<?> updateProduct(@PathVariable Integer id, @RequestParam double price){
		try {
			Product existProduct = productService.getProduct(id);

			if (existProduct != null) {
				//existTodo.setId(id);
				existProduct.setPrice(price);

				productService.saveProduct(existProduct);
			}
			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch(NoSuchElementException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/purgebyrating")
	public ResponseEntity<?> updateProduct(){
		
		productService.purgeProduct();
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
