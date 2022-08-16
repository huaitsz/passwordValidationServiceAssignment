package com.example.demo.passay;

import java.util.LinkedHashMap;
import java.util.Map;

public class CharacterRuleO {
  private CharacterRuleO() {}

  public static CharacterRule cons(final CharacterData characterData, final int numCharacters) {
    return new CharacterRule() {
      public RuleResult validate(String password) {
        if (numCharacters <= 0) {
          throw new IllegalArgumentException("argument must be greater than zero");
        }

        final String matchingChars = PasswordUtils.getMatchingCharacters(
          String.valueOf(characterData.getCharacters()),
          password,
          numCharacters);
        if (matchingChars.length() < numCharacters) {
          return new RuleResult(
            false,
            new RuleResultDetail(characterData.getErrorCode(), createRuleResultDetailParameters(matchingChars)),
            createRuleResultMetadata(password));
        }
        return new RuleResult(true, createRuleResultMetadata(password));
      }

      /**
       * Creates the parameter data for the rule result detail.
       *
       * @param  matchingChars  characters found in the password
       *
       * @return  map of parameter name to value
       */
      protected Map<String, Object> createRuleResultDetailParameters(final String matchingChars)
      {
        final Map<String, Object> m = new LinkedHashMap<>();
        m.put("minimumRequired", numCharacters);
        m.put("matchingCharacterCount", matchingChars.length());
        m.put("validCharacters", String.valueOf(characterData.getCharacters()));
        m.put("matchingCharacters", matchingChars);
        return m;
      }

      /**
       * Creates the rule result metadata.
       *
       * @param  password  data used for metadata creation
       *
       * @return  rule result metadata
       */
      private RuleResultMetadata createRuleResultMetadata(final String password)
      {
        try {
          return new RuleResultMetadata(
            RuleResultMetadata.CountCategory.valueOf(characterData.toString()),
            PasswordUtils.countMatchingCharacters(characterData.getCharacters(), password));
        } catch (IllegalArgumentException iae) {
          return new RuleResultMetadata();
        }
      }
    };
  }
}
