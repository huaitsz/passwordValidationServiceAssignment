package com.example.demo.passay;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class IllegalRegexRuleOTest {

  @Test
  void ruleValid() {
    final PasswordValidator validator = PasswordValidatorO.cons(
      IllegalRepeatedSequencesRule.cons()
    );
    final RuleResult result = validator.validate("apple");

    if (result.isValid()) {
      log.debug("Password is valid");
    } else {
      log.debug("Invalid password:");
      for (String msg : PasswordValidator.getMessages(result)) {
        log.debug(msg);
      }
    }

    Assertions.assertTrue(result.isValid());
  }

  @Test
  void ruleInvalid() {
    final PasswordValidator validator = PasswordValidatorO.cons(
      IllegalRepeatedSequencesRule.cons()
    );
    final RuleResult result = validator.validate("13133434");

    if (result.isValid()) {
      log.debug("Password is valid");
    } else {
      log.debug("Invalid password:");
      for (String msg : PasswordValidator.getMessages(result)) {
        log.debug(msg);
      }
    }

    Assertions.assertFalse(result.isValid());
  }

}
