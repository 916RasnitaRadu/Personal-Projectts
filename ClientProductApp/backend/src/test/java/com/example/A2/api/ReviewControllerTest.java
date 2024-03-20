package com.example.A2.api;

import com.example.A2.domain.dto.ProductResponseLikes;
import com.example.A2.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ReviewControllerTest {

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private ReviewController reviewController;

    private List<ProductResponseLikes> resultExpected = new ArrayList<>();

    @BeforeEach
    public void setUp()
    {
        reviewService = Mockito.mock(ReviewService.class);
        reviewController = new ReviewController(reviewService);

        ProductResponseLikes product1 = new ProductResponseLikes();
        product1.setId(2L);

        product1.setName("Shaker cu bila");

        Double avg1 = 354.0;
        product1.setLikes(avg1);
        resultExpected.add(product1);

        ProductResponseLikes product2 = new ProductResponseLikes();
        product2.setId(3L);
        product2.setName("BCAA");
        Double avg2 = 246.33333333333334;
        product2.setLikes(avg2);
        resultExpected.add(product2);
        // (4L, "Hanorac", 190.9, "Hanorac bravo", "alb", "Imbracaminte")
        ProductResponseLikes product3 = new ProductResponseLikes();
        product3.setId(4L);

        product3.setName("Hanorac alb");

        Double avg3 = 240.0;
        product3.setLikes(avg3);
        resultExpected.add(product3);
        // 3L, "BCAA", 80.9, "pastile sa dai pe gat","alb","suplimente"
        ProductResponseLikes product4 = new ProductResponseLikes();
        product4.setId(5L);
        product4.setName("Hanorac gri");

        Double avg4 = 240.0;
        product4.setLikes(avg4);
        resultExpected.add(product4);
        // 2L, "Creatina", 80.9, "creatina sa te faci mare", "alb", "suplimente"
        ProductResponseLikes product5 = new ProductResponseLikes();
        product5.setId(6L);

        product5.setName("Hanorac albastru");
        Double avg5 = 784.0;
        product5.setLikes(avg5);
        resultExpected.add(product5);
        //8L, "Shaker", 30.9, "Shaker jmk", "negru", "Accesorii"
        ProductResponseLikes product6 = new ProductResponseLikes();
        product6.setId(8L);

        product6.setName("Shaker");

        Double avg6 = 500.0;
        product6.setLikes(500.0);
        resultExpected.add(product6);
        ProductResponseLikes product8 = new ProductResponseLikes();
        product8.setId(9L);

        product8.setName("Shaker cu bila");

        Double avg8 = 19.0;
        product8.setLikes(avg8);
        resultExpected.add(product8);
        ProductResponseLikes product7 = new ProductResponseLikes();
        product7.setId(11L);

        product7.setName("Curea");
        product7.setLikes(1000.0);

        resultExpected.add(product7);
    }

//    @Test
//    void getStatsProd() {
//        when(reviewController.getStatsProd()).thenReturn(resultExpected);
//        assertEquals(8, resultExpected.size());
//        List<ProductResponseLikes> result = reviewController.getStatsProd();
//
//        assertEquals(8, result.size());
//        for (int i = 0; i < 8; i++)
//        {
//            assertEquals(resultExpected.get(i), result.get(i));
//        }
//    }
}