package com.example.tasktracker.dtos.out;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Used to wrap success messages.

 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ApiResponseDto
{
  /**
   * Indicates whether the operation was successful.
   */
  private final boolean success;

  /**
   * Descriptive message for the client.
   */
  private final String message;

  /**
   * Constructs a new {@link ApiResponseDto}.
   *
   * @param success true if the operation was successful
   * @param message message describing the result
   */
  public ApiResponseDto(final boolean success, final String message) {
    this.success = success;
    this.message = message;
  }

}
