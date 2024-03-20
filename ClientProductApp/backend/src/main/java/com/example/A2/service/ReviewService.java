package com.example.A2.service;

import com.example.A2.domain.Customer;
import com.example.A2.domain.Product;
import com.example.A2.domain.Review;

import com.example.A2.domain.dto.*;
import com.example.A2.domain.mappers.CustomerMapper;
import com.example.A2.domain.mappers.ProductMapper;
import com.example.A2.domain.mappers.ReviewMapper;
import com.example.A2.repository.CustomerRepository;
import com.example.A2.repository.ProductRepository;
import com.example.A2.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService{
    private final ReviewRepository reviewRepository;
    private final CustomerRepository customerRepository;

    private final ProductRepository productRepository;

    private final ReviewMapper reviewMapper;

    private final ProductMapper productMapper;
    private final CustomerMapper customerMapper;

    public ReviewResponse get(Long id) {
        if (reviewRepository.existsById(id))
        {
            Review review = reviewRepository.findById(id).get();
            return ReviewResponse.fromReview(review);

        }
        return null;
    }


    public ReviewResponse addService(ReviewRequest reviewRequest) {
        Optional<Customer> optionalCustomer = customerRepository.findById(reviewRequest.getIdCustomer());
        Optional<Product> optionalProduct = productRepository.findById(reviewRequest.getIdProduct());
        Review review = reviewMapper.map(reviewRequest);

        if (optionalProduct.isPresent()) review.setProduct(optionalProduct.get());
        else return null;
        if (optionalCustomer.isPresent()) review.setCustomer(optionalCustomer.get());
        else return null;

        reviewRepository.save(review);

        customerRepository.save(optionalCustomer.get());
        productRepository.save(optionalProduct.get());

        return ReviewResponse.fromReview(review);
    }


    public Page<ReviewResponse> getAll(Integer pageNumber, Integer pageSize) {
        return reviewRepository.findAll(PageRequest.of(pageNumber,pageSize)).map(ReviewResponse::fromReview);
    }


    public void deleteService(Long id) {
        if (reviewRepository.existsById(id))
        {
            reviewRepository.deleteById(id);
        }
    }


    public ReviewResponse updateService(ReviewRequest entity) {
        Review review = reviewRepository.findById(entity.getId()).
                orElseThrow(() -> new IllegalStateException("There is no such entity in the database"));

        Customer customer = customerRepository.getReferenceById(entity.getIdCustomer());
        Product product = productRepository.getReferenceById(entity.getIdProduct());
        review.setProduct(product);
        review.setCustomer(customer);
        review.setReviewText(entity.getReviewText());
        review.setCreatedAt(entity.getCreatedAt());
        review.setNumberLikes(entity.getNumberLikes());

        reviewRepository.save(review);
        return ReviewResponse.fromReview(review);
    }

    // show all the products ordered by the average of their review likes
    // for each product => avg(reviewLikes)
    public Page<ProductResponseLikes> getStatisticalReportProducts(Integer pageNumber, Integer pageSize)
    {
        return productRepository.getProductsStats(PageRequest.of(pageNumber,pageSize));
    }


    // how all the customers ordered by the average of their review likes
    public Page<CustomerResponseLikes> getStatisticalReportCustomers(Integer pageNumber, Integer pageSize)
    {
        return customerRepository.getCustomerStats(PageRequest.of(pageNumber,pageSize));
    }


}
