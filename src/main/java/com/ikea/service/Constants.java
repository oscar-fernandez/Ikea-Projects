package com.ikea.service;

public interface Constants {
	public static final String PRODUCT_TEMPLATE = "Product: %s,  code: %s, category: %s,  original price: $%.2f,  10%% discount: $%.2f,  current discounted price: $%.2f"; 
	public static final String PRODUCT_TAX_TEMPLATE = "Product name: %s, price: $%.2f, 13%% tax on product: $%.2f, total: $%.2f"; 
	public static final String PRODUCT_CATEGORY_TEMPLATE = "Product name: %s, code: %s, rating: %d, quantity: %d"; 
	public static final String PRODUCT_AVAILABLE_TEMPLATE = "%s quantity available: %s and in stock:  %s";
	public static final String PRODUCT_PURGE_TEMPLATE = "%d Records have been deleted from Product table.";
	public static final double DISCOUNT =  0.1;
	public static final double TAX = 0.13;
}
