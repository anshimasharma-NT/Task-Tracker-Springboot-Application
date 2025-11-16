package com.example.tasktracker.constants;

public interface NumericConstants {

  /**
   * No-op default method to prevent interface from being empty.
   */

  default void constantInterface() {
  }

  /** Minimum length of the password field. */
  int PASSWORD_MIN_LENGTH = 8;

  /** Maximum length of the name field. */
  int NAME_MAX_LENGTH = 100;

  /** Maximum length of the email field. */
  int EMAIL_MAX_LENGTH = 150;

  /** Maximum length of the password field. */
  int PASSWORD_MAX_LENGTH = 255;

  /** Maximum length of the title field. */
  int TITLE_LENGTH = 150;

  /** Maximum length of the description field. */
  int DESCRIPTION_LENGTH = 500;

  /** Size of the pagination. */
  int PAGINATION_SIZE = 10;

  /** Max size of the pagination. */
  int MAX_PAGE_SIZE = 100;

}
