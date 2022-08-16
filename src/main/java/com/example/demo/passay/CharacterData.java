/* See LICENSE for licensing and NOTICE for copyright. */
package com.example.demo.passay;

/**
 * Input data used by {@link CharacterRule}.
 *
 * @author  Middleware Services
 */
public interface CharacterData
{


  /**
   * Return the error code used for message resolution.
   *
   * @return  error code
   */
  String getErrorCode();


  /**
   * Returns the characters.
   *
   * @return  characters
   */
  String getCharacters();
}
