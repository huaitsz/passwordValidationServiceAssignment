package com.example.demo.passay;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class CharacterRuleOTest {
  @Test
  void ruleValid() {
    final PasswordValidator validator = PasswordValidatorO.cons(
      CharacterRuleO.cons(EnglishCharacterData.LowerCase, 1)
    );
    final RuleResult result = validator.validate("13");

    if (result.isValid()) {
      log.debug("Password is valid");
    } else {
      log.debug("Invalid password:");
      for (String msg : PasswordValidatorO.getMessages(result)) {
        log.debug(msg);
      }
    }

    assertFalse(result.isValid());
  }

  @Test
  void ruleInvalid() {
    final PasswordValidator validator = PasswordValidatorO.cons(
      CharacterRuleO.cons(EnglishCharacterData.LowerCase, 1)
    );
    final RuleResult result = validator.validate("1a3");

    if (result.isValid()) {
      log.debug("Password is valid");
    } else {
      log.debug("Invalid password:");
      for (String msg : PasswordValidatorO.getMessages(result)) {
        log.debug(msg);
      }
    }

    assertTrue(result.isValid());
  }

  @Test
  void ruleException() {
    final PasswordValidator validator = PasswordValidatorO.cons(
      CharacterRuleO.cons(EnglishCharacterData.LowerCase, 0)
    );

    IllegalArgumentException thrown = assertThrows(
      IllegalArgumentException.class,
           () -> validator.validate("1a3")
    );

    assertTrue(thrown.getMessage().contains("argument must be greater than zero"));

  }

  @Test
  void notCharacterDataAndNotMessageMapped() {
    final PasswordValidator validator = PasswordValidatorO.cons(
      CharacterRuleO.cons(
        new CharacterData() {

          @Override
          public String getErrorCode() {
            return "INSUFFICIENT_LOWERCASE2";
          }

          @Override
          public String getCharacters() {
            return "abcdefghijklmnopqrstuvwxyz";
          }

        },
        2)
    );

    final RuleResult result = validator.validate("1a3");

    if (result.isValid()) {
      log.debug("Password is valid");
    } else {
      log.debug("Invalid password:");
      for (String msg : PasswordValidatorO.getMessages(result)) {
        log.debug(msg);
      }
    }

    assertFalse(result.isValid());
  }

  @Test
  void notCharacterDataAndNotMessageMappedAndNoParameters() {
    final PasswordValidator validator = PasswordValidatorO.cons(
      new CharacterRule() {
        private int numCharacters = 1;
        public RuleResult validate(String password) {

          final String matchingChars = PasswordUtils.getMatchingCharacters(
            String.valueOf("abcdefghijklmnopqrstuvwxyz"),
            password,
            numCharacters);
          if (matchingChars.length() < numCharacters) {
            return new RuleResult(
              false,
              new RuleResultDetail("INSUFFICIENT_LOWERCASE_NOT_FOUND", null),
              new RuleResultMetadata());
          }
          return new RuleResult(true, new RuleResultMetadata());
        }
      }
    );

    final RuleResult result = validator.validate("13");

    if (result.isValid()) {
      log.debug("Password is valid");
    } else {
      log.debug("Invalid password:");
      for (String msg : PasswordValidatorO.getMessages(result)) {
        log.debug(msg);
      }
    }

    assertFalse(result.isValid());
  }


  @Test
  void errorCodeIsEmpty() {
    final PasswordValidator validator = PasswordValidatorO.cons(
      new CharacterRule() {
        private int numCharacters = 1;
        public RuleResult validate(String password) {

          final String matchingChars = PasswordUtils.getMatchingCharacters(
            String.valueOf("abcdefghijklmnopqrstuvwxyz"),
            password,
            numCharacters);
          if (matchingChars.length() < numCharacters) {
            return new RuleResult(
              false,
              new RuleResultDetail("", null),
              new RuleResultMetadata());
          }
          return new RuleResult(true, new RuleResultMetadata());
        }
      }
    );

    IllegalArgumentException thrown = assertThrows(
      IllegalArgumentException.class,
           () -> validator.validate("13")
    );

    assertTrue(thrown.getMessage().contains("Code cannot be null or empty."));
  }

  @Test
  void errorCodeIsNull() {
    final PasswordValidator validator = PasswordValidatorO.cons(
      new CharacterRule() {
        private int numCharacters = 1;
        public RuleResult validate(String password) {

          final String matchingChars = PasswordUtils.getMatchingCharacters(
            String.valueOf("abcdefghijklmnopqrstuvwxyz"),
            password,
            numCharacters);
          if (matchingChars.length() < numCharacters) {
            return new RuleResult(
              false,
              new RuleResultDetail(null, null),
              new RuleResultMetadata());
          }
          return new RuleResult(true, new RuleResultMetadata());
        }
      }
    );

    IllegalArgumentException thrown = assertThrows(
      IllegalArgumentException.class,
           () -> validator.validate("13")
    );

    assertTrue(thrown.getMessage().contains("Code cannot be null or empty."));
  }
}
