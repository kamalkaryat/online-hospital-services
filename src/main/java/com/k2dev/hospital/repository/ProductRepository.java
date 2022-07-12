package com.k2dev.hospital.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.k2dev.hospital.model.dto.Product;

public interface ProductRepository extends JpaRepository<Product, String>{ 
	
	@Query("select distinct p.productCategory from Product p")
	List<String> distinctProduct();
	
	@Query("select distinct p.productName from Product p where p.productCategory= ?1")
	List<String> findProductsName(String productCategory);

	Product findByProductCategoryAndProductName(String category, String name);
}
