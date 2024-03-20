package com.exportApp.sdk.impl;

import com.azure.storage.blob.models.BlobItem;
import com.exportApp.model.entity.FileEntity;
import com.exportApp.repository.BucketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AzureUtil {
    private final BucketRepository bucketRepository;
    public FileEntity buildBlob(BlobItem blobItem, String bucketName) {
        if (bucketRepository.getBucketByName(bucketName).isPresent()) {
            String fileUrl = "https://internship20231.blob.core.windows.net/";
            return FileEntity.builder()
                    .name(getBlobName(blobItem.getName()))
                    .type(getBlobType(blobItem.getName()))
                    .path(fileUrl + blobItem.getName())
                    .bucket(bucketRepository.getBucketByName(bucketName).get())
                    .build();
        }
        return null;
    }


    private String getBlobType(String blobPath) {
        List<String> s = splitString(blobPath);
        return "." + s.get(s.size() - 1);
    }

    private String getBlobName(String blobPath) {
        List<String> s = splitString(blobPath);
        return s.get(s.size() - 2);
    }

    private List<String> splitString(String s) {
        return Arrays.stream(s.split("[/.]")).toList();
    }
}
