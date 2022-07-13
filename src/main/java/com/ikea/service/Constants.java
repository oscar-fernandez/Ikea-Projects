package com.ikea.service;

public interface Constants {
	public static final String PRODUCT_TEMPLATE = "Product: %s,  code: %s, category: %s,  original price: $%.2f,  discount 10%%: $%.2f,  current discounted price: $%.2f"; 
	public static final String PRODUCT_TAX_TEMPLATE = "Product name: %s, price: $%.2f, tax on product: $%.2f, price after tax: $%.2f"; 
	public static final String PRODUCT_CATEGORY_TEMPLATE = "Product name: %s, code: %s, category: %s, rating: %d, quantity: %d"; 
	public static final double DISCOUNT =  0.1;
	public static final double TAX = 0.13;
}
