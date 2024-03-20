package com.exportApp.mapper;

import com.exportApp.model.entity.FileEntity;
import com.exportApp.model.fileModel.FileResponse;

public class FileMapper {
    public static FileResponse convertFileToFileResponse(FileEntity fileEntity) {
        return FileResponse.builder()
                .id(fileEntity.getId())
                .name(fileEntity.getName())
                .type(fileEntity.getType())
                .bucket(fileEntity.getBucket())
                .build();
    }
}
