package com.example.tasktracker.dtos.out;

import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object for sending user data in API responses.
 *
 * <p>This DTO is designed to exclude sensitive fields such as passwords,
 * ensuring that only non-confidential user information is returned
 * in API responses.</p>
 */

@Getter
@Setter
public final class UserResponseDTo
{

  /** Unique identifier of the user. */
  private final Long id;

  /** Name of the user. */
  private final String name;

  /** Email address of the user. */
  private final String email;

  /**
   * Constructs a new {@code UserResponseDto} instance with the provided user details.
   *
   * @param id    the unique identifier of the user
   * @param name  the name of the user
   * @param email the email address of the user
   */
  public UserResponseDTo(final Long id, final String name, final String email) {
    this.id = id;
    this.name = name;
    this.email = email;
  }
}
