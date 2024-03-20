package com.exportApp.service;

import com.exportApp.model.bucketModel.BucketResponse;
import com.exportApp.model.entity.BucketEntity;
import com.exportApp.model.entity.FileEntity;
import com.exportApp.repository.BucketRepository;
import com.exportApp.service.bucketService.BucketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.*;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class BucketServiceTest {

    @Mock
    private BucketRepository bucketRepository;

    private BucketService bucketService;

    @BeforeEach
    public void setUp() {
        bucketService = new BucketService(bucketRepository);
    }

    @Test
    void returnBuckets(){
        when(bucketRepository.findAll())
                .thenReturn(List.of(BucketEntity.builder()
                        .id(1)
                        .name("test")
                        .description(null)
                        .build()));
        List<BucketResponse> actualResponse = bucketService.returnBuckets();
        assertEquals(1, actualResponse.size());
        assertEquals(1, actualResponse.get(0).getId());
        assertEquals("test", actualResponse.get(0).getName());
        verify(bucketRepository).findAll();
    }
}
