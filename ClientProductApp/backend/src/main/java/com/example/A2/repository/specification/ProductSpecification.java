package com.example.A2.repository.specification;

import com.example.A2.domain.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {
    public static Specification<Product> priceHigherThan(Double price)
    {
        return (product, cq, cb) -> cb.greaterThan(product.get("price"),price);
    }
}
