/* See LICENSE for licensing and NOTICE for copyright. */
package com.example.demo.passay;

/**
 * Provides utility methods for this package.
 *
 * @author  Middleware Services
 */
public final class PasswordUtils
{


  /** Default constructor. */
  private PasswordUtils() {}

  /**
   * Returns all the characters in the input string that are also in the characters string.
   *
   * @param  characters  that contains characters to match
   * @param  input  to search for matches
   * @param  maximumLength maximum length of matching characters
   *
   * @return  matching characters or empty string
   */
  public static String getMatchingCharacters(final String characters, final String input, final int maximumLength)
  {
    final StringBuilder sb = new StringBuilder(input.length());
    input.chars()
      .mapToObj(e -> (char)e)
      .filter(c -> characters.indexOf(c) != -1)
      .limit(maximumLength)
      .forEach(sb::append);
    return sb.toString();
  }

  /**
   * Returns the number of characters in the supplied input that existing from the supplied characters string.
   *
   * @param  characters  that contains characters to match
   * @param  input  to search for matches
   *
   * @return  character count
   */
  public static int countMatchingCharacters(final String characters, final String input)
  {
    return (int) input.chars().filter(x -> characters.indexOf(x) != -1).count();
  }
}
