package com.example.A2.domain.dto;

import com.example.A2.domain.Product;
import com.example.A2.domain.Review;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ReviewResponse {
    private Long id;

    private Long idCustomer;

    private Long idProduct;

    private String reviewText;

    private String createdAt;

    private Integer numberLikes;

    public ReviewResponse() {}

    public ReviewResponse(Long id, Long idCustomer, Long idProduct, String reviewText,String createdAt, Integer numberLikes)
    {
        this.id = id;
        this.idCustomer = idCustomer;
        this.idProduct = idProduct;
        this.reviewText = reviewText;
        this.createdAt = createdAt;
        this.numberLikes = numberLikes;
    }
    public static ReviewResponse fromReview(Review review)
    {
        return new ReviewResponse(
                review.getId(),
                review.getCustomer().getId(),
                review.getProduct().getId(),
                review.getReviewText(),
                review.getCreatedAt(),
                review.getNumberLikes()
        );
    }
}
