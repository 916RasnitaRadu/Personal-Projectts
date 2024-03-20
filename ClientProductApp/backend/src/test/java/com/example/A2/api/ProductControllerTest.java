package com.example.A2.api;

import com.example.A2.domain.Product;
import com.example.A2.domain.dto.ProductRequest;
import com.example.A2.domain.dto.ProductResponse;
import com.example.A2.domain.dto.ProductWithReviewDTO;
import com.example.A2.domain.mappers.ProductMapper;
import com.example.A2.repository.ProductRepository;
import com.example.A2.service.ProductService;
import java.util.List;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.repository.core.support.ReactiveRepositoryFactorySupport;
import org.springframework.http.ResponseEntity;


import javax.swing.text.html.Option;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
class ProductControllerTest {


    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    List<ProductResponse> resultExpected = new ArrayList<>();

    @BeforeEach
    void setUp()
    {
        productService = Mockito.mock(ProductService.class);
        productController = new ProductController(productService);

        ProductResponse product1 = new ProductResponse();
        product1.setId(4L);
        product1.setPrice(190.9);
        product1.setName("Hanorac");
        product1.setDescription("Hanorac bravo");
        product1.setColor("alb");
        product1.setCategory("Imbracaminte");
        ProductResponse product2 = new ProductResponse();
        product2.setId(6L);
        product2.setPrice(200.9);
        product2.setName("Hanorac");
        product2.setDescription("Hanorac bravo");
        product2.setColor("albastru");
        product2.setCategory("Imbracaminte");
        ProductResponse product3 = new ProductResponse();
        product3.setId(7L);
        product3.setPrice(220.9);
        product3.setName("Hanorac");
        product3.setDescription("Hanorac bravo");
        product3.setColor("negru");
        product3.setCategory("Imbracaminte");
        ProductResponse product4 = new ProductResponse();
        product4.setId(11L);
        product4.setPrice(189.9);
        product4.setName("CURIA");
        product4.setDescription("CURIA sa nu-ti rupi schinarea");
        product4.setColor("negru");
        product4.setCategory("Accesorii");

        resultExpected.add(product1);
        resultExpected.add(product2);
        resultExpected.add(product3);
        resultExpected.add(product4);

    }

    @AfterEach
    void tearDown() throws Exception {

    }

//    @Test
//    void findProductsPriceHigherThan() {
//
//        when(productService.findProductsPriceHigherThanService(100)).thenReturn(resultExpected);
//
//        List<ProductResponse> result = productController.findProductsPriceHigherThan(100);
//        Assertions.assertEquals(4, result.size());
//        for (int i = 0; i < 4; i++)
//        {
//            Assertions.assertEquals(result.get(i),resultExpected.get(i));
//        }
//
//    }


    @Test
    void findProductById() {
        ProductWithReviewDTO productRequest = new ProductWithReviewDTO();
        productRequest.setId(1L);
        productRequest.setName("Proteine");
        productRequest.setPrice(100.9);
        productRequest.setDescription("ceva de genu");
        productRequest.setCategory( "suplimente");
        productRequest.setColor("alb");
        //when(productService.addService(productRequest)).thenReturn(new ProductResponse());
        when(productService.get(1L)).thenReturn(Optional.of(productRequest));

        ResponseEntity<ProductWithReviewDTO> product = productController.findProductById(1L);

        Assertions.assertEquals(productRequest, product.getBody());
    }
}