package com.example.A2.service;

import com.example.A2.domain.Customer;
import com.example.A2.domain.Review;
import com.example.A2.domain.dto.CustomerRequest;
import com.example.A2.domain.dto.CustomerResponse;
import com.example.A2.domain.dto.CustomerWithReviewDTO;
import com.example.A2.domain.dto.ReviewRequest;
import com.example.A2.domain.mappers.CustomerMapper;
import com.example.A2.repository.CustomerRepository;
import com.example.A2.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerService  {
    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    private final ReviewRepository reviewRepository;


    public CustomerWithReviewDTO get(Long id) {
        if (customerRepository.existsById(id))
        {
            Optional<Customer> customer = customerRepository.findById(id);

            if (customer.isEmpty()) return null;

            CustomerWithReviewDTO customerDTO = new CustomerWithReviewDTO();
            customerDTO.setId(customer.get().getId());
            customerDTO.setAge(customer.get().getAge());
            customerDTO.setFirstName(customer.get().getFirstName());
            customerDTO.setLastName(customer.get().getLastName());
            customerDTO.setAddress(customer.get().getAddress());
            customerDTO.setEmail(customer.get().getEmail());
            customerDTO.setReviewList(customer.get().getReviewList().stream().map(review -> new ReviewRequest(review.getId(),
                    review.getCustomer().getId(), review.getProduct().getId(), review.getReviewText(), review.getCreatedAt(), review.getNumberLikes())).collect(Collectors.toList()));
            return customerDTO;
        }
        return null;
    }


    public CustomerResponse addService(CustomerRequest customerRequest) {
        Customer customer = customerMapper.map(customerRequest);
        customerRepository.save(customer);
        return customerMapper.map(customer);
    }


    public Page<CustomerResponse> getAll(Integer pageNumber, Integer pageSize) {
        return customerRepository.findAll(PageRequest.of(pageNumber, pageSize)).map(CustomerResponse::fromCustomer);
    }


    public void deleteService(Long id) {
        if (customerRepository.existsById(id))
        {
            customerRepository.deleteById(id);
        }
    }


    public CustomerResponse updateService(CustomerRequest entity) {
        Customer customer = customerRepository.findById(entity.getId()).
                orElseThrow(() -> new IllegalStateException("There is no such entity in the database"));

        customer.setFirstName(entity.getFirstName());
        customer.setLastName(entity.getLastName());
        customer.setAge(entity.getAge());
        customer.setEmail(entity.getEmail());
        customer.setAddress(entity.getAddress());

        customerRepository.save(customer);
        return CustomerResponse.fromCustomer(customer);
    }

    public void addReviewsService(Long id, List<Long> reviews)
    {
        int min;
        Customer customer = customerRepository.findById(id).
                orElseThrow(() -> new IllegalStateException("There is no such entity in the database"));
        List<Review> customerReviewList = customer.getReviewList();
        List<Review> newReviewList = new ArrayList<>();

        if (reviews.size() <= customerReviewList.size())
        {
            min = reviews.size();
        }
        else { min = customerReviewList.size(); }

        for (int i = 0; i < min; i++) {
            if (reviewRepository.existsById(reviews.get(i))) {
                Review review = customerReviewList.get(i);
                Review newReview = new Review();
                newReview.setId(reviews.get(i));
                newReview.setCustomer(review.getCustomer());
                newReview.setProduct(review.getProduct());
                newReview.setReviewText(review.getReviewText());
                newReview.setCreatedAt(review.getCreatedAt());
                newReview.setNumberLikes(review.getNumberLikes());
                newReviewList.add(newReview);
                customerReviewList.remove(review);
                reviewRepository.delete(review);
            }
        }
        customer.setReviewList(newReviewList);

        customerRepository.save(customer);
    }
}
