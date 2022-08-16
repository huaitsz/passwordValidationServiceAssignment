package com.example.demo.passay;

public interface Rule {
  /**
   * Validates the supplied password data per the requirements of this rule.
   *
   * @param  passwordData  to verify (not null).
   *
   * @return  details on password verification
   *
   * @throws  NullPointerException  if the rule data is null.
   */
  RuleResult validate(String password);
}
