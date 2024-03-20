package com.exportApp.controller.bucketController;

import com.exportApp.model.entity.BucketEntity;
import com.exportApp.service.bucketService.BucketService;
import com.exportApp.service.fileService.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.exportApp.controller.controller_response.ResponseHandler.generateResponse;

@RestController
@RequestMapping("/buckets")
@CrossOrigin
public class BucketController {
    private final BucketService bucketService;
    private final FileService fileService;

    @Autowired
    public BucketController(BucketService bucketService, FileService fileService) {
        this.bucketService = bucketService;
        this.fileService = fileService;
    }

    @GetMapping("/files")
    public ResponseEntity<Object> getFilesByBucketName(@RequestParam String bucketName) {
        try {
            BucketEntity bucketEntity = bucketService.getBucketByBucketName(bucketName);
            return generateResponse(fileService.returnFilesFromBucketName(bucketEntity), "Bucket " + bucketName + " selected", HttpStatus.OK);
        } catch (Exception e) {
            return generateResponse(null, "An error occurred while selecting bucket " + bucketName, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<Object> getBuckets() {
        try {
            return generateResponse(bucketService.returnBuckets(), "Welcome home!", HttpStatus.OK);
        } catch (Exception e) {
            return generateResponse(null, "An error occurred while fetching buckets!", HttpStatus.BAD_REQUEST);
        }
    }
}
