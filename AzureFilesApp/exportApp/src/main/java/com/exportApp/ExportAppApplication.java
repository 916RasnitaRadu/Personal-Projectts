package com.exportApp;

import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ExportAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExportAppApplication.class, args);
    }
}
