package com.example.demo.passay;

import java.util.List;
import java.util.stream.Collectors;

public interface PasswordValidator {

  /**
   * Validates the supplied password data against the rules in this validator.
   *
   * @param  passwordData  to validate
   *
   * @return  rule result
   */
  RuleResult validate(final String password);

  /** Message resolver. */
  final MessageResolver messageResolver = new PropertiesMessageResolver();

  /**
   * Returns a list of human-readable messages by iterating over the details in a failed rule result.
   *
   * @param  result  failed rule result.
   *
   * @return  list of human-readable messages describing the reason(s) for validation failure.
   */
  static List<String> getMessages(final RuleResult result)
  {
    return result.getDetails().stream().map(messageResolver::resolve).collect(Collectors.toList());
  }
}
