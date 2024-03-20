package com.example.A2.domain.dto;

import com.example.A2.domain.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class CustomerResponse {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String address;

    private Integer age;

    private long numberReviews = 0;

    public CustomerResponse() {}

    public CustomerResponse(Long id, String firstName, String lastName, String email, String address, Integer age, long numberReviews) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.age = age;
        this.numberReviews = numberReviews;
    }

    public static CustomerResponse fromCustomer(Customer customer)
    {
        return new CustomerResponse(
                customer.getId(),
                customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getAddress(), customer.getAge(), customer.getReviewList().size()
        );
    }
}
