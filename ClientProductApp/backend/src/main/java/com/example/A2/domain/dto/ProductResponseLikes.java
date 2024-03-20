package com.example.A2.domain.dto;

import lombok.Data;

@Data
public class ProductResponseLikes  {
    private Long id;

    private String name;

    private Double likes;

    public ProductResponseLikes(Long id, String name, Double likes) {
        this.id= id;
        this.name = name;
        this.likes = likes;
    }

    public ProductResponseLikes() {}
}
