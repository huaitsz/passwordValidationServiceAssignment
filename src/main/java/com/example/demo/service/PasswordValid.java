package com.example.demo.service;

import com.example.demo.vo.ValidationResult;

import io.vavr.concurrent.Future;

public interface PasswordValid {
  Future<ValidationResult> validate(String password);
}
