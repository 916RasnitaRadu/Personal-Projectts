package com.example.A2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.A2.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>, ICustomerRepository {
}
