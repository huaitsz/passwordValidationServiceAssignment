package com.example.demo.controller;

import java.util.concurrent.CompletableFuture;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.PasswordValidO;
import com.example.demo.vo.ValidationResult;

@RestController
@RequestMapping("/password")
public class PasswordVerification {

  @PostMapping("/verification")
  CompletableFuture<ValidationResult> validate(@RequestParam String password) {
    return PasswordValidO.cons().validate(password).toCompletableFuture();
  }
}
