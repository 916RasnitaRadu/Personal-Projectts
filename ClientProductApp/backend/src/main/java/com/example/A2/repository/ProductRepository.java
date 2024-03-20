package com.example.A2.repository;

import com.example.A2.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product>, IProductRepository {
    Product findProductById(Long id);

}
