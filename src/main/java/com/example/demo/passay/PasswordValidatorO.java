package com.example.demo.passay;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PasswordValidatorO {

  /** Message resolver. */
  private static final MessageResolver messageResolver = new PropertiesMessageResolver();

  /**
   * Returns a list of human-readable messages by iterating over the details in a failed rule result.
   *
   * @param  result  failed rule result.
   *
   * @return  list of human-readable messages describing the reason(s) for validation failure.
   */
  public static List<String> getMessages(final RuleResult result)
  {
    return result.getDetails().stream().map(messageResolver::resolve).collect(Collectors.toList());
  }

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
