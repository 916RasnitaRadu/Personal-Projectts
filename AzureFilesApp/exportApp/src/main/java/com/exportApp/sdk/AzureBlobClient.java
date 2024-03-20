package com.exportApp.sdk;

import com.azure.storage.blob.BlobServiceClient;
import com.exportApp.exception_handler.exception.AuthenticationFailedException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AzureBlobClient {
    void connect() throws AuthenticationFailedException, javax.mail.AuthenticationFailedException;

    void addBucketsToDb() throws AuthenticationFailedException;
    void addFilesToDB() throws AuthenticationFailedException;
}
