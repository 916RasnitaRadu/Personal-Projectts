package com.exportApp.service.folderService;

import com.exportApp.exception_handler.exception.NotFoundException;
import com.exportApp.mapper.FileConverter;
import com.exportApp.mapper.FileResponse;
import com.exportApp.model.entity.BucketEntity;
import com.exportApp.model.entity.FileEntity;
import com.exportApp.model.entity.User;
import com.exportApp.repository.BucketRepository;
import com.exportApp.repository.FileRepository;
import com.exportApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FolderService {
    private FileRepository fileRepository;

    private String folderName;

    private String csvFilePath;

    @Value("${global.baseExportPath}")
    private String basePath;

    @Autowired
    public FolderService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public void createAndExportCSVFiles(String[] exportFileList) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
        folderName = basePath + File.separator + "exportFolder_" + timeStamp;
        String fileName = exportFileList[0].split("\\.")[0];
        Optional<BucketEntity> bucket = fileRepository.getBucketNameByFileName(fileName);
        if (bucket.isPresent()) {
            createFiles(bucket.get().getName());
            exportFiles(exportFileList, bucket.get().getName(), timeStamp);
        }
    }

    private void createFiles(String bucketName) {
        String csvFileName = bucketName + ".csv";
        csvFilePath = folderName + File.separator + csvFileName;

        File folder = new File(folderName);
        if (!folder.exists()) {
            if (folder.mkdirs()) {
                System.out.println("Folder created successfully.");
            } else {
                System.err.println("Failed to create folder.");
            }
        }
    }

    private void exportFiles(String[] exportFileList, String bucketName, String timeStamp) throws IOException {
        List<FileResponse> files = getFilesFromFileList(exportFileList);

        File csvFile = new File(csvFilePath);

        try (FileWriter fileWriter = new FileWriter(csvFile)) {
            fileWriter.append(constructCsvHeader());
            fileWriter.append("\n");

            for (FileResponse fileResponse : files) {
                fileWriter.write(fileResponse.getName());
                fileWriter.append(",");
                fileWriter.write(fileResponse.getPath());
                fileWriter.append(",");
                fileWriter.write(fileResponse.getType());

                if (fileResponse.getMetadata() != null) {
                    for (String k : getMetadata()) {
                        if (fileResponse.getMetadata().containsKey(k)) {
                            fileWriter.append(", ").append(fileResponse.getMetadata().get(k));
                        } else {
                            fileWriter.append(", ");
                        }
                    }
                }
                fileWriter.append("\n");

                FileUtils.writeByteArrayToFile(new File(folderName + "/" + fileResponse.getName() + fileResponse.getType()), fileResponse.getContent());
                exportFileForDownload(bucketName, exportFileList);
            }
        } catch (IOException e) {
            throw new RuntimeException("An error occurred while exporting files!");
        }
    }

    public void exportFileForDownload(String bucketName, String[] exportFileList){
        List<FileResponse> files = getFilesFromFileList(exportFileList);

        File baseFile = new File(basePath + "/" + bucketName + ".csv");

        try (FileWriter fileWriter = new FileWriter(baseFile)) {
            fileWriter.append(constructCsvHeader());
            fileWriter.append("\n");

            for (FileResponse fileResponse : files) {
                fileWriter.write(fileResponse.getName());
                fileWriter.append(",");
                fileWriter.write(fileResponse.getPath());
                fileWriter.append(",");
                fileWriter.write(fileResponse.getType());

                if (fileResponse.getMetadata() != null) {
                    for (String k : getMetadata()) {
                        if (fileResponse.getMetadata().containsKey(k)) {
                            fileWriter.append(", ").append(fileResponse.getMetadata().get(k));
                        } else {
                            fileWriter.append(", ");
                        }
                    }
                }
                fileWriter.append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException("An error occurred while exporting files!");
        }
    }

    public StringBuilder constructCsvHeader(){

        StringBuilder csvFileHeader = new StringBuilder("Name, File Path, Type");

        for (String k : getMetadata()) {
            csvFileHeader.append(", ").append(k);
        }

        return csvFileHeader;
    }

    public List<String> getMetadata(){
        Set<String> metadataSet = new HashSet<>();

        List<Map<String, String>> filesMetadata = fileRepository.getFilesMetadata();

        filesMetadata.stream()
                .forEach(metadata -> metadataSet.addAll(metadata.keySet()));

        List<String> metadata = new ArrayList<>(metadataSet);
        Collections.sort(metadata);
        return metadata;
    }

    private List<FileResponse> getFilesFromFileList(String[] exportFileList) {
        List<FileEntity> files = new ArrayList<>();
        for (String fileName : exportFileList
        ) {
            String[] name = fileName.split("\\.");
            Optional<FileEntity> file = fileRepository.findFileByNameAndType(name[0], "." + name[1]);
            file.ifPresent(files::add);
        }

        return files.stream()
                .map(FileConverter::convertFileToFileResponse)
                .collect(Collectors.toList());
    }

    public byte[] downloadCSVFile(String bucketName) throws IOException {
        File file = new File(basePath + "/" + bucketName + ".csv");

        if (file.exists()) {
            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                byte[] fileContent = new byte[(int) file.length()];
                fileInputStream.read(fileContent);
                return fileContent;
            }
        } else {
            throw new FileNotFoundException("CSV file not found");
        }
    }

}
