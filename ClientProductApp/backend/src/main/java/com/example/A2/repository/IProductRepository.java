package com.example.A2.repository;

import com.example.A2.domain.dto.ProductResponseLikes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductRepository {
    Page<ProductResponseLikes> getProductsStats(Pageable pageable);
}
