package com.example.demo.passay;

import java.util.LinkedHashMap;
import java.util.Map;

public class LengthComplexityRuleO {

  /** Error code for password too short. */
  public static final String ERROR_CODE_MIN = "TOO_SHORT";

  /** Error code for password too long. */
  public static final String ERROR_CODE_MAX = "TOO_LONG";

  private LengthComplexityRuleO() {}

  public static LengthComplexityRule cons(final int minLength, final int maxLength) {
    return new LengthComplexityRule() {
      public RuleResult validate(String password) {
        final RuleResult result = new RuleResult();
        final int length = password.length();
        if (length < minLength) {
          result.addError(ERROR_CODE_MIN, createRuleResultDetailParameters());
        } else if (length > maxLength) {
          result.addError(ERROR_CODE_MAX, createRuleResultDetailParameters());
        }
        result.setMetadata(createRuleResultMetadata(password));
        return result;
      }

      /**
       * Creates the parameter data for the rule result detail.
       *
       * @return  map of parameter name to value
       */
      private Map<String, Object> createRuleResultDetailParameters()
      {
        final Map<String, Object> m = new LinkedHashMap<>();
        m.put("minimumLength", minLength);
        m.put("maximumLength", maxLength);
        return m;
      }

      /**
       * Creates the rule result metadata.
       *
       * @param  password  data used for metadata creation
       *
       * @return  rule result metadata
       */
      protected RuleResultMetadata createRuleResultMetadata(final String password)
      {
        return new RuleResultMetadata(RuleResultMetadata.CountCategory.Length, password.length());
      }
    };
  }
}
