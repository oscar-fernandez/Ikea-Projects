package com.ikea.repository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;


import com.ikea.model.Product;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ProductRepository extends JpaRepository<Product, Integer>{
	 List<Product> findByCode(String code);
     List<Product> findByCategory(String category);
     List<Product> findByRating(int rating);
     
     @Modifying
     @Query("DELETE FROM Product WHERE rating = (SELECT MIN(rating) FROM Product)")
     int deleteByRating();
     
     @Modifying
     @Query("SELECT p FROM Product p WHERE p.rating = (SELECT MAX(rating) FROM Product)")
     List<Product> findByMaxRating();
     

}
