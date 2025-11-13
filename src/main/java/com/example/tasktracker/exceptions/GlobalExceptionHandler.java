package com.example.tasktracker.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Handles exceptions globally across all controllers.
 *
 * <p>Ensures consistent HTTP responses for validation and unexpected errors.</p>
 */
@ControllerAdvice
public class GlobalExceptionHandler {

  /** Logger for this class. */
  private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  /**
   * Handles {@link ValidationException} and returns a 400 response.
   *
   * @param ex the validation exception
   * @return response with error message
   */
  @ExceptionHandler(ValidationException.class)
  public ResponseEntity<String> handleValidationException(final ValidationException ex) {
    LOGGER.warn("Validation failed: {}", ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }

  /**
   * Handles any unhandled exceptions and returns a 500 response.
   *
   * @param ex the generic exception
   * @return response with generic error message
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleGenericException(final Exception ex) {
    LOGGER.error("Unexpected error occurred", ex);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("An unexpected error occurred. Please try again later.");
  }
}
