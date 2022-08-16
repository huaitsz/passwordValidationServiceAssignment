package com.example.demo.vo;

import java.util.List;

import lombok.Data;

@Data
public class ValidationResult {
  private final Boolean isValid;
  private final List<String> messages;
}
