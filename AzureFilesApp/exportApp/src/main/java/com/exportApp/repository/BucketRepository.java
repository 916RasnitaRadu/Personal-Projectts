package com.exportApp.repository;

import com.exportApp.model.entity.BucketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BucketRepository extends JpaRepository<BucketEntity, Integer> {
    @Query("FROM BucketEntity b WHERE b.name = :name")
    Optional<BucketEntity> getBucketByName(@Param("name") String name);

    @Query("SELECT b.name FROM BucketEntity b")
    List<String> getBucketsNames();
}
