package com.exportApp.sdk.impl;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobContainerItem;
import com.azure.storage.blob.models.BlobItem;
import com.exportApp.exception_handler.exception.AuthenticationFailedException;
import com.exportApp.model.entity.BucketEntity;
import com.exportApp.model.entity.FileEntity;
import com.exportApp.repository.BucketRepository;
import com.exportApp.repository.FileRepository;
import com.exportApp.sdk.AzureBlobClient;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AzureBlobClientImpl implements AzureBlobClient {

    private BlobServiceClient blobServiceClient;

    private final BucketRepository bucketRepository;
    private final FileRepository fileRepository;
    private final AzureUtil azureUtil;

    public void connect() throws AuthenticationFailedException {
        String sasToken = "";
        blobServiceClient = new BlobServiceClientBuilder()
                .endpoint("")
                .sasToken(sasToken)
                .buildClient();
        try {
            blobServiceClient.getAccountUrl();
        } catch (Exception e) {
            throw new AuthenticationFailedException("Authentication for azure failed!");
        }
    }

    @PostConstruct
    public void addBucketsToDb() throws AuthenticationFailedException {
        connect();
        if (blobServiceClient == null) {
            throw new AuthenticationFailedException("Not connected to storage account!");
        }

        for (BlobContainerItem blobContainerItem : blobServiceClient.listBlobContainers()) {
            if (bucketRepository.getBucketByName(blobContainerItem.getName()).isEmpty()) {
                BucketEntity bucket = BucketEntity.builder()
                        .name(blobContainerItem.getName())
                        .description("")
                        .build();
                bucketRepository.save(bucket);
            }
        }
        addFilesToDB();
    }

    public void addFilesToDB() throws AuthenticationFailedException {

        List<BlobContainerItem> containers = blobServiceClient.listBlobContainers().stream().toList();

        for (BlobContainerItem containerItem : containers) {
            BlobContainerClient blobContainerClient = blobServiceClient.getBlobContainerClient(containerItem.getName());
            List<BlobItem> blobs = blobContainerClient.listBlobs().stream().toList();

            for (BlobItem blobItem : blobs) {
                BlobClient blob = blobContainerClient.getBlobClient(blobItem.getName());

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                blob.downloadStream(outputStream);

                FileEntity fileEntity = azureUtil.buildBlob(blobItem, containerItem.getName());
                fileEntity.setContent(outputStream.toByteArray());
                fileEntity.setMetadata(blob.getProperties().getMetadata());

                if (fileRepository.findFileByPath(fileEntity.getPath()).isEmpty()) {
                    fileRepository.save(fileEntity);
                }
            }
        }
    }
}
