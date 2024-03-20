package com.exportApp.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "buckets")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BucketEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @OneToMany(
            mappedBy = "bucket",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<FileEntity> files = new HashSet<>();

}
