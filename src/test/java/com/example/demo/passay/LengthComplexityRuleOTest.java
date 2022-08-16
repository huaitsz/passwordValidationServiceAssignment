package com.example.demo.passay;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.example.demo.passay.RuleResultMetadata.CountCategory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class LengthComplexityRuleOTest {
  @Test
  void ruleValid() {
    final PasswordValidator validator = PasswordValidatorO.cons(
      LengthComplexityRuleO.cons(3, 7)
    );
    final RuleResult result = validator.validate("133");

    if (result.isValid()) {
      log.debug("Password is valid");
    } else {
      log.debug("Invalid password:");
      for (String msg : PasswordValidator.getMessages(result)) {
        log.debug(msg);
      }
    }

    Assertions.assertEquals(3, result.getMetadata().getCount(CountCategory.Length));

    Assertions.assertTrue(result.getMetadata().hasCount(CountCategory.Length));

    Assertions.assertTrue(result.isValid());
  }

  @Test
  void ruleInvalid() {
    final PasswordValidator validator = PasswordValidatorO.cons(
      LengthComplexityRuleO.cons(3, 7)
    );
    final RuleResult result = validator.validate("13");
    Assertions.assertFalse(result.isValid());

    final RuleResult result2 = validator.validate("12345678");
    Assertions.assertFalse(result2.isValid());


  }
}
