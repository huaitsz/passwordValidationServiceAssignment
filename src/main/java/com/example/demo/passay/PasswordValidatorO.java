package com.example.demo.passay;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PasswordValidatorO {

  private PasswordValidatorO() {}

  public static PasswordValidator cons(final MessageResolver resolver, final List<? extends Rule> rules) {
    return new PasswordValidator() {

      /** Password rules. */
      private final List<? extends Rule> passwordRules = rules;

      /** Message resolver. */
      private final MessageResolver messageResolver = resolver;

      /**
       * Returns a list of human-readable messages by iterating over the details in a failed rule result.
       *
       * @param  result  failed rule result.
       *
       * @return  list of human-readable messages describing the reason(s) for validation failure.
       */
      public List<String> getMessages(final RuleResult result) {
        return result.getDetails().stream().map(messageResolver::resolve).collect(Collectors.toList());
      }

      public RuleResult validate(final String password) {
        return passwordRules.stream()
          .map(r -> r.validate(password))
          .reduce(new RuleResult(), (r1, r2) -> r1.combine(r2));
      }
    };
  }


  public static PasswordValidator cons(final Rule... rules) {
    return cons(new PropertiesMessageResolver(), Arrays.asList(rules));
  }

  /**
   * Creates a new password validator with a {@link PropertiesMessageResolver}.
   *
   * @param  rules  to validate
   */
  public static PasswordValidator cons(final List<? extends Rule> rules) {
    return cons(new PropertiesMessageResolver(), rules);
  }
}
