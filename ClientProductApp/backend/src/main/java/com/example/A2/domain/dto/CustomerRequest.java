package com.example.A2.domain.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {
    private Long id;

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
}
