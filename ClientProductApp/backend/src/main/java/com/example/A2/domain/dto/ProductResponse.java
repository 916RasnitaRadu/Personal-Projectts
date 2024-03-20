package com.example.A2.domain.dto;

import com.example.A2.domain.Product;
import com.example.A2.domain.Review;
import lombok.Data;

import java.util.List;


@Data
public class ProductResponse {
    private Long id;

    private String name;

    private double price;

    private String description;

    private String color;

    private String category;

    private long nrReviews = 0;

    public ProductResponse() {}

    public ProductResponse(Long id, String  name, double price, String description, String color, String category, long nrReviews) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
        this.color = color;
        this.nrReviews = nrReviews;
    }


    public static ProductResponse fromProduct(Product product)
    {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getColor(),
                product.getCategory(),
                product.getReviewsList().size()
        );
    }
}
