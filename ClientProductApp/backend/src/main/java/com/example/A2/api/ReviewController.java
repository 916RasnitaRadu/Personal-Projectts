package com.example.A2.api;

import com.example.A2.domain.dto.*;
import com.example.A2.service.ReviewService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RequestMapping("/review")
@ComponentScan({"com.example.A2.service.ReviewService"} )
@RestController
public class ReviewController {
    private final ReviewService service;

    public ReviewController(ReviewService service) {
        this.service = service;
    }

    @GetMapping(path = "/{id}")
    public ReviewResponse findReviewById(@PathVariable Long id) { return service.get(id);}

    @GetMapping(path="/all")
    public Page<ReviewResponse> getAllReviews(@RequestParam Integer pageNumber, @RequestParam
    @Min(value=4, message = "Page size should be at least 4")
    @Max(value=10, message = "Page size should be at most 10" )
    Integer pageSize)
    {
        return service.getAll(pageNumber, pageSize);
    }

    @PostMapping(path = "/")
    public ReviewResponse addReview(@RequestBody @Valid ReviewRequest review)
    {
        return service.addService(review);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteReview(@PathVariable Long id) {
        service.deleteService(id);
    }

    @PutMapping(path = "/")
    public ReviewResponse update(@RequestBody @Valid ReviewRequest review) {
        return service.updateService(review);
    }

    @GetMapping(path = "/statProd")
    public Page<ProductResponseLikes> getStatsProd(@RequestParam Integer pageNumber, @RequestParam
    @Min(value=4, message = "Page size should be at least 4")
    @Max(value=10, message = "Page size should be at most 10" )
    Integer pageSize) {
        return service.getStatisticalReportProducts(pageNumber, pageSize);
    }

    @GetMapping(path = "/statCust")
    public Page<CustomerResponseLikes> getStatsCust(@RequestParam Integer pageNumber, @RequestParam
    @Min(value=4, message = "Page size should be at least 4")
    @Max(value=10, message = "Page size should be at most 10" )
    Integer pageSize) {
        return service.getStatisticalReportCustomers(pageNumber, pageSize);
    }
}
