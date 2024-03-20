package com.example.A2.domain.dto;

import lombok.Data;

@Data
public class CustomerResponseLikes {
    private Long id;

    private String firstName;

    private String lastName;

    private Double likes;

    public CustomerResponseLikes(Long id, String firstName, String lastName, Double likes) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.likes = likes;
    }

    public CustomerResponseLikes() {}
}
