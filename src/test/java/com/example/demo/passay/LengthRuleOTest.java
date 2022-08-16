package com.example.demo.passay;

import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import static com.example.demo.passay.RuleTest.codes;

class LengthRuleOTest {

  static class Passwords implements ArgumentsProvider {
      @Override
      public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        return Stream.of(
          Arguments.of(LengthRuleO.cons(8, 10), "p4T3#6Tu", null),
          Arguments.of(LengthRuleO.cons(8, 10), "p4T3t#6Tu", null),
          Arguments.of(LengthRuleO.cons(8, 10), "p4T3to#6Tu", null),
          Arguments.of(
            LengthRuleO.cons(8, 10),
            "p4T36",
            codes(LengthRuleO.ERROR_CODE_MIN)
          ),
          Arguments.of(LengthRuleO.cons(0, 10), "p4T3#6Tu", null),
          Arguments.of(LengthRuleO.cons(0, 10), "p4T3t#6Tu", null),
          Arguments.of(LengthRuleO.cons(0, 10), "p4T3to#6Tu", null),
          Arguments.of(LengthRuleO.cons(0, 10), "p4T36", null),
          Arguments.of(
            LengthRuleO.cons(0, 10),
            "p4T3j76rE@#",
            codes(LengthRuleO.ERROR_CODE_MAX)
          ),
          Arguments.of(LengthRuleO.cons(8, Integer.MAX_VALUE), "p4T3#6Tu", null),
          Arguments.of(LengthRuleO.cons(8, Integer.MAX_VALUE), "p4T3t#6Tu", null),
          Arguments.of(LengthRuleO.cons(8, Integer.MAX_VALUE), "p4T3to#6Tu", null),
          Arguments.of(
            LengthRuleO.cons(8, 10),
            "p4T36",
            codes(LengthRuleO.ERROR_CODE_MIN)
          ),
          Arguments.of(LengthRuleO.cons(8, Integer.MAX_VALUE), "p4T3j76rE@#", null)
        );
      }
  }

  static class Messages implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
      return Stream.of(
        Arguments.of(
          LengthRuleO.cons(8, 10), "p4T3j76rE@#",
          new String[] {
            String.format("Password must be no more than %s characters in length.", 10)
          }
        ),
        Arguments.of(
          LengthRuleO.cons(8, 10), "p4T36",
          new String[] {
            String.format("Password must be %s or more characters in length.", 8)
          }
        )
      );
    }
  }
}
