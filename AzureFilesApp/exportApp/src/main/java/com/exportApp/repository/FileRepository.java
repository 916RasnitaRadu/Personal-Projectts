package com.exportApp.repository;

import com.exportApp.model.entity.BucketEntity;
import com.exportApp.model.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Integer> {
    @Query("SELECT f FROM FileEntity f WHERE f.name = :name AND f.type = :type")
    Optional<FileEntity> findFileByNameAndType(@Param("name") String name, @Param("type") String type);

    @Query("SELECT f FROM FileEntity f WHERE f.path = :path")
    Optional<FileEntity> findFileByPath(@Param("path") String path);

    List<FileEntity> findByBucketName(String bucketName);

    @Query("SELECT f FROM FileEntity f WHERE f.bucket = :bucket")
    List<FileEntity> findByBucketID(@Param("bucket") BucketEntity bucket);

    @Query("SELECT f.bucket FROM FileEntity f WHERE f.name = :name")
    Optional<BucketEntity> getBucketNameByFileName(@Param("name") String name);

    @Query("SELECT f.metadata FROM FileEntity f")
    List<Map<String, String>> getFilesMetadata();
}
