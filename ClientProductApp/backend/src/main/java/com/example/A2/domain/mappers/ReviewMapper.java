package com.example.A2.domain.mappers;

import com.example.A2.domain.Review;
import com.example.A2.domain.dto.ReviewRequest;
import com.example.A2.domain.dto.ReviewResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ReviewMapper {
    Review map(ReviewRequest reviewRequest);

    ReviewResponse map(Review review);

    List<ReviewResponse> map(List<Review> list);

}
