package com.example.tasktracker.exceptions.custom;

/**
 * {@code TaskAlreadyCompletedException} is a custom runtime exception that indicates
 * that a task status is already completed.
 */

public class TaskAlreadyCompletedException extends RuntimeException {

  /**
   * Constructs a new {@code TaskAlreadyCompletedException} with the specified detail message.
   *
   * @param message the detail message explaining the reason for the exception.
   */
  public TaskAlreadyCompletedException(String message) {
    super(message);
  }
}
