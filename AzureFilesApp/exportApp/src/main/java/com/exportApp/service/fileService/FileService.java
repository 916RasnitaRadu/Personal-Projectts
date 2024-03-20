package com.exportApp.service.fileService;

import com.exportApp.mapper.FileMapper;
import com.exportApp.model.entity.BucketEntity;
import com.exportApp.model.fileModel.FileResponse;
import com.exportApp.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;

    public List<FileResponse> returnFiles() {
        return fileRepository.findAll().stream()
                .map(FileMapper::convertFileToFileResponse)
                .collect(Collectors.toList());
    }

    public List<FileResponse> returnFilesFromBucketName(BucketEntity bucket) {
        return fileRepository.findByBucketID(bucket).stream()
                .map(FileMapper::convertFileToFileResponse)
                .collect(Collectors.toList());
    }
}
