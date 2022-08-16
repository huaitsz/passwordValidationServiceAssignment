package com.example.demo.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.example.demo.vo.ValidationResult;

import io.vavr.concurrent.Future;

class PasswordValidOTest {
  @Test
  void validate() {
    PasswordValid passwordValid = PasswordValidO.cons();
    Future<ValidationResult> futRes = passwordValid.validate("!!!!!!!!!");
    ValidationResult r = futRes.get();

    Assertions.assertFalse(r.getIsValid());
    Assertions.assertTrue(r.getMessages().size() > 0);
  }
}
