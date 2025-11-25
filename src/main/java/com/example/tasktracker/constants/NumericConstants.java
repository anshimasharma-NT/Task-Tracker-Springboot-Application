package com.example.tasktracker.constants;

public interface NumericConstants {

  /**
   * No-op default method to prevent interface from being empty.
   */

  default void constantInterface() {
  }

  /**
   * Maximum length of the name field.
   */
   int NAME_MAX_LENGTH = 100;

  /**
   * Maximum length of the email field.
   */
   int EMAIL_MAX_LENGTH = 150;

  /**
   * Date Pattern.
   */
  String DATE_PATTERN = "MM-dd-uuuu";
  /**
   * Default Page size.
   */
  String DEFAULT_PAGE_SIZE = "10";
  /**
   * Default Page number.
   */
  String DEFAULT_PAGE_NUMBER = "0";

}
