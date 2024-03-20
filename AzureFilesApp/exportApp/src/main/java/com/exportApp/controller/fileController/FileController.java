package com.exportApp.controller.fileController;

import com.exportApp.exception_handler.exception.NoSelectedFilesException;
import com.exportApp.model.fileModel.FileResponse;
import com.exportApp.service.fileService.FileService;
import com.exportApp.service.folderService.FolderService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.exportApp.controller.controller_response.ResponseHandler.generateResponse;

@RestController
@RequestMapping("/files")
@CrossOrigin
public class FileController {
    private final FileService fileService;
    private final FolderService folderService;

    @Autowired
    public FileController(FileService fileService, FolderService folderService) {
        this.fileService = fileService;
        this.folderService = folderService;
    }

    @GetMapping
    private ResponseEntity<List<FileResponse>> getFiles() {
        return ResponseEntity.ok(fileService.returnFiles());
    }

    @PostMapping("/export")
    public ResponseEntity<Object> exportFiles(@RequestBody String[] nodeNames) throws IOException {
        try {
            if(nodeNames == null || nodeNames.length == 0){
                throw new NoSelectedFilesException();
            }

            folderService.createAndExportCSVFiles(nodeNames);
            return generateResponse(null, "Exporting CSV file...", HttpStatus.OK);
        } catch(NoSelectedFilesException e){
            return generateResponse(null, "Please select some files first!", HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            return generateResponse(null, "An error occurred while exporting the CSV file!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/download/{bucketName}")
    public ResponseEntity<byte[]> downloadCSVFile(@PathVariable String bucketName) throws IOException {
        byte[] fileContent = folderService.downloadCSVFile(bucketName);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/csv"));
        headers.setContentDispositionFormData("attachment", bucketName);

        return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
    }
}
