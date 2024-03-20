package com.exportApp.model.fileModel;

import com.exportApp.model.entity.BucketEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileResponse {

    private int id;
    private String name;
    private String type;
    private BucketEntity bucket;
}
