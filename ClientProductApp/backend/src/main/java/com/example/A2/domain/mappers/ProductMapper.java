package com.example.A2.domain.mappers;


import com.example.A2.domain.Product;
import com.example.A2.domain.dto.ProductRequest;
import com.example.A2.domain.dto.ProductResponse;
import com.example.A2.domain.dto.ProductResponseLikes;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {
    Product map(ProductRequest customerRequest);

    ProductResponse map(Product customer);

    List<ProductResponse> map(List<Product> customerList);

}
