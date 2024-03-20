package com.example.A2.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;



@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Customer {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "customer_id") Long id;

    @NotBlank(message = "Please enter the name of the product")
    @Size(min=4, message = "Name should be at least 4 characters long")
    private String firstName;

    @NotBlank(message = "Please enter the name of the product")
    @Size(min=4, message = "Name should be at least 4 characters long")
    private String lastName;

    @Email(message = "Please enter a valid email address")
    private String email;

    private String address;

    @NotNull(message = "Please enter the price of the product")
    @Min(value = 18, message = "The age must be at least 18")
    private Integer age;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "customer")
    private List<Review> reviewList;

}
