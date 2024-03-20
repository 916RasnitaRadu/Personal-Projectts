package com.example.A2.api;

import com.example.A2.domain.Review;
import com.example.A2.domain.dto.*;
import com.example.A2.service.CustomerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.A2.domain.Customer;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/customer")
@RestController
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService service;


    @GetMapping(path = "/{id}")
    public ResponseEntity findCustomerById(@PathVariable Long id)
    {
        CustomerWithReviewDTO customerDTO = service.get(id);
        if (customerDTO == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(customerDTO);
    }

    @GetMapping(path = "/all")
    public @ResponseBody Page<CustomerResponse> getAllCustomers(@RequestParam Integer pageNumber, @RequestParam
    @Min(value=4, message = "Page size should be at least 4")
    @Max(value=10, message = "Page size should be at most 10" )
    Integer pageSize) {
        return service.getAll(pageNumber, pageSize);
    }

    @PostMapping(path = "/")
    public ResponseEntity<CustomerResponse> addCustomer(@RequestBody @Valid CustomerRequest customer) {
        CustomerResponse customerResponse = service.addService(customer);
        return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        service.deleteService(id);
    }

    @PutMapping(path = "/")
    public CustomerResponse update(@RequestBody @Valid CustomerRequest newCustomer) {
        return service.updateService(newCustomer);
    }

    @PatchMapping(path = "/{id}/review")
    public void addReviews(@PathVariable Long id ,@RequestBody NumberRequest reviewIdList)
    {
        List<Long> reviews = reviewIdList.getNumbers();
        service.addReviewsService(id, reviews);
    }



}
