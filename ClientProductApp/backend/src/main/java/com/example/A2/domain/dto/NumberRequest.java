package com.example.A2.domain.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
public class NumberRequest {
    private List<Long> numbers;
}
