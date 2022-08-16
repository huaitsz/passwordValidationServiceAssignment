package com.example.demo.passay;

public interface LengthComplexityRule extends Rule{
  /** Error code for password too short. */
  public static final String ERROR_CODE_MIN = "TOO_SHORT";

  /** Error code for password too long. */
  public static final String ERROR_CODE_MAX = "TOO_LONG";
}