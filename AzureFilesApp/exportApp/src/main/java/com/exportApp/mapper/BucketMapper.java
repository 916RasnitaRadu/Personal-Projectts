package com.exportApp.mapper;

import com.exportApp.model.bucketModel.BucketResponse;
import com.exportApp.model.entity.BucketEntity;

public class BucketMapper {
    public static BucketResponse convertBucketToBucketResponse(BucketEntity bucket){
        return BucketResponse.builder()
                .id(bucket.getId())
                .name(bucket.getName())
                .description(bucket.getDescription())
                .build();
    }
}
