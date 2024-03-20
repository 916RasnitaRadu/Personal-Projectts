package com.example.A2.domain.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private Long id;

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

}
