package com.exportApp.model.bucketModel;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BucketResponse {
    private int id;
    private String name;
    private String description;
}
