package com.example.Server.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "transaction")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String type;

    @Column
    private String category;

    @Column
    private double amount;

    @Column
    @JsonFormat(pattern = "MMM d, yyyy hh:mm:ss a")
    private Date timestamp;

}
