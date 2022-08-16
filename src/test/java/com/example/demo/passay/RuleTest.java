package com.example.demo.passay;

import java.util.Properties;
import java.util.ResourceBundle;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

class RuleTest {

  /** test message resolver. */
  public static final MessageResolver TEST_RESOLVER = new ResourceBundleMessageResolver(
    ResourceBundle.getBundle("passay-test"));

  /** empty message resolver. */
  public static final MessageResolver EMPTY_RESOLVER = new PropertiesMessageResolver(new Properties());

  /**
   * @param  rule  to check password with
   * @param  password  to check
   * @param  errorCodes  Array of error codes to be produced on a failed password validation attempt. A null value
   *                     indicates that password validation should succeed.
   */
  @ParameterizedTest
  @ArgumentsSource(LengthRuleOTest.Passwords.class)
  @ArgumentsSource(IllegalRepeatedSequencesRuleTest.Passwords.class)
  @ArgumentsSource(CharacterRuleOTest.Passwords.class)
  @ArgumentsSource(PasswordValidatorOTest.Passwords.class)
  void checkPassword(final Rule rule, final String password, final String[] errorCodes) {

    final RuleResult result = rule.validate(password);
    if (errorCodes != null) {
      Assertions.assertFalse(result.isValid());
      Assertions.assertEquals(errorCodes.length, result.getDetails().size());
      for (String code : errorCodes) {
        Assertions.assertTrue(hasErrorCode(code, result));
      }
    } else {
      Assertions.assertTrue(result.isValid());
    }
  }

  /**
   * @param  rule  to check password with
   * @param  password  to check
   * @param  messages  Array of messages to be produced on a failed password validation attempt
   */
  @ParameterizedTest
  @ArgumentsSource(LengthRuleOTest.Messages.class)
  @ArgumentsSource(IllegalRepeatedSequencesRuleTest.Messages.class)
  @ArgumentsSource(CharacterRuleOTest.Messages.class)
  @ArgumentsSource(PasswordValidatorOTest.Messages.class)
  void checkMessage(final Rule rule, final String password, final String[] messages)
  {
    final RuleResult result = rule.validate(password);
    Assertions.assertFalse(result.isValid());
    Assertions.assertEquals(messages.length, result.getDetails().size());
    for (int i = 0; i < result.getDetails().size(); i++) {
      final RuleResultDetail detail = result.getDetails().get(i);
      Assertions.assertEquals(messages[i], TEST_RESOLVER.resolve(detail));
      Assertions.assertNotNull(EMPTY_RESOLVER.resolve(detail));
    }
  }

  /**
   * Determines whether the given error code is found among the details of the give rule validation result.
   *
   * @param  code  to search for in result details.
   * @param  result  to search for given code.
   *
   * @return  True if code is found among result details, false otherwise.
   */
  public static boolean hasErrorCode(final String code, final RuleResult result)
  {
    for (RuleResultDetail detail : result.getDetails()) {
      if (code.equals(detail.getErrorCode())) {
        return true;
      }
    }
    return false;
  }

  /**
   * Converts one or more error codes to a string array.
   *
   * @param  codes  One or more error codes.
   *
   * @return  Array of error codes.
   */
  public static String[] codes(final String... codes)
  {
    return codes;
  }
}
