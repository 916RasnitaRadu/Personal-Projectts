package com.example.A2.repository;

import com.example.A2.domain.dto.CustomerResponseLikes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICustomerRepository {
    Page<CustomerResponseLikes> getCustomerStats(Pageable pageable);
}
