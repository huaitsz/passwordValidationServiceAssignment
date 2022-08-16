package com.example.demo.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.example.demo.configuration.Configuration;
import com.example.demo.vo.ValidationResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.vavr.concurrent.Future;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class PasswordValidOTest {
  @Test
  void validate() {
    PasswordValid passwordValid = PasswordValidO.cons();
    Future<ValidationResult> futRes = passwordValid.validate("!!!!!!!!!");
    Configuration configuration = new Configuration();
    ObjectMapper mapper = configuration.objectMapper();
    ValidationResult r = futRes.get();

    Assertions.assertNotNull(r);

    try {
      log.debug(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(r));
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }
}
