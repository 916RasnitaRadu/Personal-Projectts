package com.example.Server.dto;

import com.example.Server.model.Type;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TransactionDTO {
    private Long id;

    private String title;

    private String type;

    private String category;

    private double amount;

    @JsonFormat(pattern = "MMM d, yyyy hh:mm:ss a")
    private Date timestamp;
}
