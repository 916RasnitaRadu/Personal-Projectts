package com.example.A2.service;

import com.example.A2.domain.Product;
import com.example.A2.domain.dto.ProductRequest;
import com.example.A2.domain.dto.ProductResponse;
import com.example.A2.domain.dto.ProductWithReviewDTO;
import com.example.A2.domain.mappers.ProductMapper;
import com.example.A2.repository.ProductRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductServiceTest {

    @MockBean
    private ProductRepository productRepository;
    @MockBean
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productRepository = Mockito.mock(ProductRepository.class);
        productMapper = Mockito.mock(ProductMapper.class);
        productService = new ProductService(productRepository, productMapper);
    }


    @Test
    void findProductsPriceHigherThanService() {
        ProductResponse product1 = new ProductResponse();
        product1.setId(9L);
        product1.setPrice(50.9);
        product1.setName("Shaker cu bila");
        product1.setDescription("Shaker jmk cu bila");
        product1.setColor("negru");
        product1.setCategory("Accesorii");
        ProductResponse product2=  new ProductResponse();
        product2.setPrice(175.9);
        product2.setId(5L);
        product2.setName("Hanorac");
        product2.setDescription("Hanorac bravo");
        product2.setColor("gri");
        product2.setCategory("Imbracaminte");
        ProductResponse product3 = new ProductResponse();
        product3.setId(4L);
        product3.setPrice(190.9);
        product3.setName("Hanorac");
        product3.setDescription("Hanorac bravo");
        product3.setColor("alb");
        product3.setCategory("Imbracaminte");
    }
}