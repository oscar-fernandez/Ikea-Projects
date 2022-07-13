package com.ikea.repository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;


import com.ikea.model.Product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ProductRepository extends JpaRepository<Product, Integer>{
	 List<Product> findByCode(String code);
     
     @Query("SELECT t from Product t WHERE username=?1 AND description LIKE %?2%")
     List<Product> fingByUsernameAndDescription(String username, String description);
     
     List<Product> findByCategory(String category);
     
     List<Product> findByRating(int rating);
     
     @Query("SELECT MAX(rating) FROM Product")
     int getMaxRating(); 
     
     @Query("SELECT MIN(rating) FROM Product")
     int getMinRating(); 
     
     @Query("DELETE from Product  WHERE Rating = ?1")
     void deleteByRating(int rating); 
     
     

}
