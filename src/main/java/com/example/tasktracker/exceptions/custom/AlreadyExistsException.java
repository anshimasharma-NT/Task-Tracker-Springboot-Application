package com.example.tasktracker.exceptions.custom;


/**
 * {@code AlreadyExistsException} is a custom runtime exception that indicates
 * that a requested entity is already exists in the data source.
 */
public class AlreadyExistsException extends RuntimeException {

  /**
   * Constructs a new {@code AlreadyExistsException} with the specified detail message.
   *
   * @param message the detail message explaining the reason for the exception.
   */
  public AlreadyExistsException(String message){
    super(message);
  }
}
