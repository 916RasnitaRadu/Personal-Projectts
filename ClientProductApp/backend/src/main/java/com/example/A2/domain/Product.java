package com.example.A2.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.util.List;


@Entity
@Table(name="product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Product {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "product_id")
    Long id;

    @NotBlank(message = "Please enter the name of the product")
    @Size(min=4, message = "Name should be at least 4 characters long")
    private String name;

    @NotNull(message = "Please enter the price of the product")
    @Min(value = 0, message = "The price can not be a negative number")
    private double price;

    private String description;

    private String color;

    @NotBlank(message = "Please enter the category of the product")
    private String category;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private List<Review> reviewsList;

}
