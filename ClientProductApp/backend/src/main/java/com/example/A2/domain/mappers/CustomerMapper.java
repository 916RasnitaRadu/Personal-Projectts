package com.example.A2.domain.mappers;

import com.example.A2.domain.Customer;
import com.example.A2.domain.dto.CustomerRequest;
import com.example.A2.domain.dto.CustomerResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface CustomerMapper {
    Customer map(CustomerRequest customerRequest);

    CustomerResponse map(Customer customer);

    List<CustomerResponse> map(List<Customer> customerList);
}
