package com.exportApp.service;

import com.azure.core.http.rest.PagedIterable;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobContainerItem;
import com.azure.storage.blob.models.BlobItem;
import com.exportApp.model.entity.FileEntity;
import com.exportApp.sdk.AzureBlobClient;
import com.exportApp.sdk.impl.AzureUtil;
import com.exportApp.service.fileService.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.AuthenticationFailedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;
import static reactor.core.publisher.Mono.when;

@SpringBootTest
public class FileServiceTest {
    @Mock
    private BlobServiceClient blobServiceClient;
    @Mock
    private AzureBlobClient azureBlobClient;
    @Mock
    private FileService fileService;
    @Mock
    private AzureUtil azureUtil;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSuccessfulConnectionToAzure() {
        BlobServiceClientBuilder blobServiceClientBuilder = mock(BlobServiceClientBuilder.class);

        doReturn(blobServiceClientBuilder).when(blobServiceClientBuilder).endpoint(anyString());
        doReturn(blobServiceClientBuilder).when(blobServiceClientBuilder).sasToken(anyString());
        doReturn(blobServiceClient).when(blobServiceClientBuilder).buildClient();
        doReturn("https://example.blob.core.windows.net").when(blobServiceClient).getAccountUrl();

        assertDoesNotThrow(() -> azureBlobClient.connect());

        assertThat(blobServiceClient.getAccountUrl(), is("https://example.blob.core.windows.net"));
    }

    @Test
    public void testAddFilesToDB() throws AuthenticationFailedException {
        BlobContainerClient blobContainerClient = mock(BlobContainerClient.class);
        FileEntity fileEntity = mock(FileEntity.class);

        PagedIterable<BlobContainerItem> containerItems = blobServiceClient.listBlobContainers();
        doReturn(containerItems).when(blobServiceClient).listBlobContainers();

        PagedIterable<BlobItem> blobServiceClients = blobContainerClient.listBlobs();
        doReturn(blobServiceClients).when(blobServiceClient).getBlobContainerClient(anyString());

        doReturn(fileEntity).when(azureUtil).buildBlob(any(), anyString());

        assertDoesNotThrow(() -> azureBlobClient.addFilesToDB());
        azureBlobClient.addFilesToDB();

        assertThat(fileEntity, is(notNullValue()));
    }

}
