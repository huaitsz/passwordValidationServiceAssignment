package com.example.demo.service;

import com.example.demo.passay.*;
import com.example.demo.vo.ValidationResult;

import io.vavr.concurrent.Future;

public class PasswordValidO {
  private PasswordValidO() {}

  public static PasswordValid cons() {
    return (String password) -> {
      final PasswordValidator validator = PasswordValidatorO.cons(
        // at least one lower-case character
        CharacterRuleO.cons(EnglishCharacterData.LowerCase, 1),
        // at least one digit character
        CharacterRuleO.cons(EnglishCharacterData.Digit, 1),
        // length between 8 and 16 characters
        LengthRuleO.cons(5, 12),
        // rejects passwords that contain repeating sequences of characters
        IllegalRepeatedSequencesRule.cons()
      );

      return Future.of(() -> validator.validate(password))
        .map(r -> new ValidationResult(r.isValid(), validator.getMessages(r)));
    };

  }

}
