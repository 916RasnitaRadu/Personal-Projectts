package com.exportApp.service.bucketService;

import com.exportApp.exception_handler.exception.NotFoundException;
import com.exportApp.mapper.BucketMapper;
import com.exportApp.model.bucketModel.BucketResponse;
import com.exportApp.model.entity.BucketEntity;
import com.exportApp.repository.BucketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BucketService {
    private final BucketRepository bucketRepository;

    @Autowired
    public BucketService(BucketRepository bucketRepository) {
        this.bucketRepository = bucketRepository;
    }

    public List<BucketResponse> returnBuckets() {
        return bucketRepository.findAll().stream()
                .map(BucketMapper::convertBucketToBucketResponse)
                .collect(Collectors.toList());
    }

    public List<String> getBucketsNames() {
        List<String> bucketsNames = bucketRepository.getBucketsNames();
        return bucketsNames;
    }

    public BucketEntity getBucketByBucketName(String bucketName) {
        return bucketRepository.getBucketByName(bucketName)
                .orElseThrow(() -> new NotFoundException("Bucket with name " + bucketName + " not found!"));
    }
}
