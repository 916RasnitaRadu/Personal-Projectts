package com.exportApp.mapper;

import com.exportApp.model.entity.FileEntity;

public class FileConverter {
    public static FileResponse convertFileToFileResponse(FileEntity file){
        return FileResponse.builder()
                .name(file.getName())
                .type(file.getType())
                .path(file.getPath())
                .content(file.getContent())
                .metadata(file.getMetadata())
                .build();
    }
}
