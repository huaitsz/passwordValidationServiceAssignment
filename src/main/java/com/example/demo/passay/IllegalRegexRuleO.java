package com.example.demo.passay;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IllegalRegexRuleO {

  /** Error code for regex validation failures. */
  public static final String ERROR_CODE = "ILLEGAL_MATCH";

  private IllegalRegexRuleO() {}

  public static IllegalRegexRule cons(final String regex) {
    return cons(regex, ERROR_CODE);
  }

  public static IllegalRegexRule cons(final String regex, final String errorCode) {
    return new IllegalRegexRule() {
      /** Regex pattern. */
      private final Pattern pattern = Pattern.compile(regex);

      @Override
      public RuleResult validate(String password) {
        final RuleResult result = new RuleResult();
        final Matcher m = pattern.matcher(password);
        final Set<String> matches = new HashSet<>();
        while (m.find()) {
          final String match = m.group();
          if (!matches.contains(match)) {
            result.addError(errorCode, createRuleResultDetailParameters(match));
            matches.add(match);
          }
        }
        return result;
      }

      /**
       * Creates the parameter data for the rule result detail.
       *
       * @param  match  matching regex
       *
       * @return  map of parameter name to value
       */
      private Map<String, Object> createRuleResultDetailParameters(final String match)
      {
        final Map<String, Object> m = new LinkedHashMap<>();
        m.put("match", match);
        m.put("pattern", pattern);
        return m;
      }
    };
  }
}
