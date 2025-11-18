package com.example.tasktracker.exceptions.handler;

import com.example.tasktracker.exceptions.custom.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for the Task Tracker application.
 *
 * <p>This class handles all custom and generic exceptions thrown by controllers
 * and provides appropriate HTTP status codes and error messages in the response.</p>
 *
 * <p>Each exception type is mapped to a specific HTTP status code according to
 * the application's error handling strategy.</p>
 */
@ControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Handles {@link UserNotFoundException} and returns HTTP 404 (Not Found).
   *
   * @param ex the thrown {@link UserNotFoundException}
   * @return a {@link ResponseEntity} containing the error message
   */
  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<String> handleUserNotFound(UserNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }

  /**
   * Handles {@link TaskNotFoundException} and returns HTTP 404 (Not Found).
   *
   * @param ex the thrown {@link TaskNotFoundException}
   * @return a {@link ResponseEntity} containing the error message
   */
  @ExceptionHandler(TaskNotFoundException.class)
  public ResponseEntity<String> handleTaskNotFound(TaskNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }

  /**
   * Handles {@link ValidationException} and returns HTTP 400 (Bad Request).
   *
   * @param ex the thrown {@link ValidationException}
   * @return a {@link ResponseEntity} containing the error message
   */
  @ExceptionHandler(ValidationException.class)
  public ResponseEntity<String> handleValidation(ValidationException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }

  /**
   * Handles {@link AuthenticationException} and returns HTTP 401 (Unauthorized).
   *
   * @param ex the thrown {@link AuthenticationException}
   * @return a {@link ResponseEntity} containing the error message
   */
  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<String> handleAuthentication(AuthenticationException ex) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
  }

  /**
   * Handles {@link TaskAlreadyCompletedException} and returns HTTP 208 (Already Reported).
   *
   * @param ex the thrown {@link TaskAlreadyCompletedException}
   * @return a {@link ResponseEntity} containing the error message
   */
  @ExceptionHandler(TaskAlreadyCompletedException.class)
  public ResponseEntity<String> handleStatus(Exception ex) {
    return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(ex.getMessage());
  }

  /**
   * Handles {@link AlreadyExistsException} and returns HTTP 409 (Conflict).
   *
   * @param ex the thrown {@link AlreadyExistsException}
   * @return a {@link ResponseEntity} containing the error message
   */
  @ExceptionHandler(AlreadyExistsException.class)
  public ResponseEntity<String> handleAlreadyExists(AlreadyExistsException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
  }

  /**
   * Handles all other generic exceptions and returns HTTP 500 (Internal Server Error).
   *
   * @param ex the thrown generic {@link Exception}
   * @return a {@link ResponseEntity} containing a generic error message with exception details
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleGeneric(Exception ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Internal server error: " + ex.getMessage());
  }
}
