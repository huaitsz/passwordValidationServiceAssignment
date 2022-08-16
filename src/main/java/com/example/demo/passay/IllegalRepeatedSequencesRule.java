package com.example.demo.passay;

public class IllegalRepeatedSequencesRule {
  public static final String ERROR_CODE = "ILLEGAL_MATCH_REPEATED_SEQUENCES";
  public static final String REGEX  = "(.{2,})\\1";

  public static IllegalRegexRule cons() {
    return IllegalRegexRuleO.cons(REGEX, ERROR_CODE);
  }
}
