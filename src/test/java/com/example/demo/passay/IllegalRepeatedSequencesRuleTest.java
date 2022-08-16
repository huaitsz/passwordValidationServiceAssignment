package com.example.demo.passay;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

import static com.example.demo.passay.RuleTest.codes;

class IllegalRepeatedSequencesRuleTest {

  static class Passwords implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
      return Stream.of(
        // test valid password
        Arguments.of(IllegalRepeatedSequencesRule.cons(),
          "p4zRcv8#n65",
          null
        ),
        // test entire password
        Arguments.of(IllegalRepeatedSequencesRule.cons(),
          "abab",
          codes(IllegalRepeatedSequencesRule.ERROR_CODE)
        ),
        // test find password
        Arguments.of(IllegalRepeatedSequencesRule.cons(),
          "p4zRcvabab8#n65",
          codes(IllegalRepeatedSequencesRule.ERROR_CODE)
        ),
        // test multiple matches
        Arguments.of(IllegalRepeatedSequencesRule.cons(),
          "p4zRcvabab8#ncdcd65",
          codes(IllegalRepeatedSequencesRule.ERROR_CODE,IllegalRepeatedSequencesRule.ERROR_CODE)
        ),
        // test duplicate matches
        Arguments.of(IllegalRepeatedSequencesRule.cons(),
          "p4zRcvabab8#ncdcd63abab45",
          codes(IllegalRepeatedSequencesRule.ERROR_CODE, IllegalRepeatedSequencesRule.ERROR_CODE)
        )
      );
    }
  }

  static class Messages implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
      return Stream.of(
        Arguments.of(
          IllegalRepeatedSequencesRule.cons(),
          "abab",
          new String[] {
            String.format("Password contains the repeating sequences of characters '%1$s'.", "abab")
          }
        ),
        Arguments.of(
          IllegalRepeatedSequencesRule.cons(),
          "p4zRcvabab8#n65",
          new String[] {
            String.format("Password contains the repeating sequences of characters '%1$s'.", "abab")
          }
        ),
        Arguments.of(
          IllegalRepeatedSequencesRule.cons(),
          "p4zRcvabab8#ncdcd65",
          new String[] {
            String.format("Password contains the repeating sequences of characters '%1$s'.", "abab"),
            String.format("Password contains the repeating sequences of characters '%1$s'.", "cdcd")
          }
        ),
        Arguments.of(
          IllegalRepeatedSequencesRule.cons(),
          "p4zRcvabab8#ncdcd63abab45",
          new String[] {
            String.format("Password contains the repeating sequences of characters '%1$s'.", "abab"),
            String.format("Password contains the repeating sequences of characters '%1$s'.", "cdcd")
          }
        )
      );
    }
  }
}
