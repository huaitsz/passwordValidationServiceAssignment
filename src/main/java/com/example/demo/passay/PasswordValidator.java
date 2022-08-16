package com.example.demo.passay;

import java.util.List;

public interface PasswordValidator extends Rule{

  public List<String> getMessages(final RuleResult result);
}
