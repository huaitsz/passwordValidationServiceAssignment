package com.example.demo.passay;

import java.util.Arrays;
import java.util.List;

public class PasswordValidatorO {
  private PasswordValidatorO() {}

  public static PasswordValidator cons(final Rule... rules) {
    return new PasswordValidator() {
      public RuleResult validate(final String password) {
        final List<? extends Rule> passwordRules = Arrays.asList(rules);
        return passwordRules.stream()
          .map(r -> r.validate(password))
          .reduce(new RuleResult(), (r1, r2) -> r1.combine(r2));
      }
    };
  }
}
