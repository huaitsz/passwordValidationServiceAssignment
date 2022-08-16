package com.example.demo.passay;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.example.demo.passay.RuleTest.codes;

class PasswordValidatorOTest {

  /** Test checker. */
  private static final List<Rule> rules = new ArrayList<>();

  /** For testing. */
  private static final PasswordValidator validator;

  static {
    validator = PasswordValidatorO.cons(rules);

  }

  static {
    rules.add(IllegalRepeatedSequencesRule.cons());
    rules.add(LengthRuleO.cons(8, 10));
    rules.add(CharacterRuleO.cons(EnglishCharacterData.LowerCase, 1));
    rules.add(CharacterRuleO.cons(EnglishCharacterData.Digit, 1));
  }

  static class Passwords implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
      return Stream.of(

        /** all digits */
        Arguments.of(validator,
          "33334ddd",
          codes(
            IllegalRepeatedSequencesRule.ERROR_CODE
          )
        ),
        /** all non-alphanumeric */
        Arguments.of(validator,
          "$@(#$%#@*!",
          codes(
            EnglishCharacterData.LowerCase.getErrorCode(),
            EnglishCharacterData.Digit.getErrorCode()
          )
        ),
        /** too short */
        Arguments.of(validator,
          "3a",
          codes(
            LengthRuleO.ERROR_CODE_MIN
          )
        ),
        /** too long */
        Arguments.of(validator,
          "applejack39832",
          codes(
            LengthRuleO.ERROR_CODE_MAX
          )
        ),
        /** all non-alphanumeric, too long, repeated sequences */
        Arguments.of(validator,
          "$@($%#)(!)(!@$",
          codes(
            EnglishCharacterData.LowerCase.getErrorCode(),
            EnglishCharacterData.Digit.getErrorCode(),
            LengthRuleO.ERROR_CODE_MAX,
            IllegalRepeatedSequencesRule.ERROR_CODE
          )
        ),
        /** lowercase numerical digits */
        Arguments.of(validator,
          "applejack2",
          null
        )
      );
    }
  }

  static class Messages implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
      return Stream.of(
        Arguments.of(
          validator,
          "$@($%#)(!)(!@$",
          new String[] {
            String.format("Password contains the repeating sequences of characters '%1$s'.", ")(!)(!"),
            String.format("Password must be no more than %s characters in length.", 10),
            String.format("Password must contain %1$s or more lowercase characters.", 1),
            String.format("Password must contain %1$s or more digit characters.", 1)
          }
        )
      );
    }
  }
}
