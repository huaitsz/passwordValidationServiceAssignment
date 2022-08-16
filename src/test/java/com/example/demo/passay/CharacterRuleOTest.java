package com.example.demo.passay;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

import static com.example.demo.passay.RuleTest.codes;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class CharacterRuleOTest {

  static class Passwords implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
      return Stream.of(
        Arguments.of(CharacterRuleO.cons(EnglishCharacterData.LowerCase, 1),
          "REGDFa3432",
          null
        ),
        Arguments.of(CharacterRuleO.cons(EnglishCharacterData.LowerCase, 1),
          "REGDF3432",
          codes(EnglishCharacterData.LowerCase.getErrorCode())
        ),
        Arguments.of(CharacterRuleO.cons(EnglishCharacterData.Digit, 1),
          "pzRcv8#n",
          null
        ),
        Arguments.of(CharacterRuleO.cons(EnglishCharacterData.Digit, 1),
          "pzRcv#n",
          codes(EnglishCharacterData.Digit.getErrorCode())
        )
      );
    }
  }

  static class Messages implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
      return Stream.of(
        Arguments.of(
          CharacterRuleO.cons(EnglishCharacterData.LowerCase, 1),
          "REGDF3432",
          new String[] {
            String.format("Password must contain %1$s or more lowercase characters.", 1)
          }
        ),
        Arguments.of(
          CharacterRuleO.cons(EnglishCharacterData.Digit, 1),
          "pzRcv#n",
          new String[] {
            String.format("Password must contain %1$s or more digit characters.", 1)
          }
        )
      );
    }
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
      for (String msg : validator.getMessages(result)) {
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
      for (String msg : validator.getMessages(result)) {
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
