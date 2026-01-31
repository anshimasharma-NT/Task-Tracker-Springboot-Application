package com.example.tasktracker.exceptions.custom;


/**
 * {@code TaskNotFoundException} is a custom runtime exception that indicates
 * that a requested entity was not found in the data source.
 */
public class NotFoundException extends RuntimeException {

  /**
   * Constructs a new {@code TaskNotFoundException} with the specified detail message.
   *
   * @param message the detail message explaining the reason for the exception.
   */
  public NotFoundException(String message) {
    super(message);
  }
}