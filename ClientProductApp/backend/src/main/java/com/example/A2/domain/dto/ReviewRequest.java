package com.example.A2.domain.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequest {
    private Long id;

    @NotNull(message = "The ID of the Customer can not be null")
    @Min(value=1, message = "The ID of the Customer must be a valid number")
    private Long idCustomer;

    @NotNull(message = "The ID of the Product can not be null")
    @Min(value=1, message = "The ID of the Customer must be a valid number")
    private Long idProduct;

    @NotBlank(message = "Please enter the review")
    @Size(min=4, message = "The review should be at least 4 characters long")
    private String reviewText;

    @NotBlank(message = "Please enter the creation date")
    @Size(min=7, message = "Date should be at least 7 characters long")
    private String createdAt;

    @Min(value=0,message="Please enter a valid number")
    private Integer numberLikes;


}
