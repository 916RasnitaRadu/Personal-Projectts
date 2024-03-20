package com.example.A2.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name="review")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @NotNull
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @NotNull
    private Product product;

    @NotBlank(message = "Please enter the review")
    @Size(min=4, message = "The review should be at least 4 characters long")
    private String reviewText;

    private String createdAt;

    @Column(name = "numberLikes")
    private Integer numberLikes;

}
