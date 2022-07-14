package com.ikea.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import com.ikea.model.Product;
import com.ikea.repository.ProductRepository;

//The class contains logic for idea inventory system. IT will retrieve, add calculate tax and discounted price.
@Service
@Transactional
public class ProductService {
	@Autowired
	ProductRepository prodRep;

	// i - Gets all the inventory products
	public List<Product> getProducts( ) {

		return prodRep.findAll();
	}

	// Gets inventory products by id.
	public Product getProduct(int id){  

		return prodRep.findById(id).get();
	}

	// ii Filter Product by given category
	public List<String> getProducts( String category) {

		List<Product> products = prodRep.findByCategory(category);
		List<String> filterProducts = new ArrayList<String>();

		for (Product p: products){
			filterProducts.add(String.format(Constants.PRODUCT_CATEGORY_TEMPLATE, p.getName(), p.getCode(), p.getRating(), p.getQuantity())); 
		}

		return filterProducts;
	}

	// iii Create and Update product - This method will add product to inventory system
	public void saveProduct(Product newProduct) {
		prodRep.save(newProduct);
	}

	// iv Product Availability - Get available products
	public String getAvailableProducts(String code) {
		String filterProducts = "";

		List<Product> products = prodRep.findByCode(code);

		for (Product p: products) {
			filterProducts = String.format(Constants.PRODUCT_AVAILABLE_TEMPLATE, p.getName(), p.getQuantity(), (p.getInStock()? "Yes":"No"));
		}

		return filterProducts;
	}

	// v - Product Cost - This module will calculate the tax for the given product in inventory and product with tax.
	public List<String> getProductAfterTax(String category) {
		List<String> taxedProducts = new ArrayList<String>();

		List<Product> products = prodRep.findByCategory(category);

		for (Product p: products){

			double taxAmount = p.getPrice() * Constants.TAX;
			double priceAfterTax = p.getPrice() + taxAmount;

			taxedProducts.add(String.format(Constants.PRODUCT_TAX_TEMPLATE, p.getName(), p.getPrice(),	taxAmount, priceAfterTax));     

		}

		return taxedProducts;
	}

	// vi Product Discount - The method will calculate discount on products and return array of string contain original price and discounted price of products.
	public List<String> getProductDiscount() {
		List<String> discountedProd = new ArrayList<String>();

		List<Product> products  = prodRep.findByMaxRating();

		for (Product p: products){
			double discount = p.getPrice() * Constants.DISCOUNT;
			double discountedPrice = p.getPrice() - discount;

			discountedProd.add( String.format(Constants.PRODUCT_TEMPLATE, p.getName(), p.getCode(), p.getCategory(), 
					p.getPrice(),  discount, discountedPrice ));
		}

		return discountedProd;
	}


	// viii - The method will calculate discount on products and return array of string contain original price and discounted price of products.
	public String purgeProduct() {
		int numOfRecDeleted = prodRep.deleteByRating();

		return String.format(Constants.PRODUCT_PURGE_TEMPLATE, numOfRecDeleted);	
	}	
}
