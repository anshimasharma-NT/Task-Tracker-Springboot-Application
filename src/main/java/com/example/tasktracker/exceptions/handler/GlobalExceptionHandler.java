package com.example.tasktracker.exceptions.handler;

import com.example.tasktracker.dtos.out.ApiResponseDto;
import com.example.tasktracker.exceptions.custom.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for the application.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Handles the custom EmailAlreadyExistsException.
   * @param ex the exception instance
   * @return ResponseEntity containing a RegisterOutDTO with error details
   */
  @ExceptionHandler(AlreadyExistsException.class)
  public ResponseEntity<ApiResponseDto> handleEmailExists(final AlreadyExistsException ex) {
    ApiResponseDto response = new ApiResponseDto(false, ex.getMessage());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
  }
  /**
   * Handles validation failures for user input .
   * @param ex  thrown ValidationException
   * @return ResponseEntity containing a RegisterOutDTO with HTTP status 400
   */
  @ExceptionHandler(ValidationException.class)
  public ResponseEntity<ApiResponseDto> handleValidationException(final ValidationException ex) {
    ApiResponseDto response = new ApiResponseDto(false, ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }
  /**
   * Handles invalid credential failures for user input .
   * @param ex  thrown InvalidCredentialsException
   * @return ResponseEntity containing a SuccessResponseDTO
   */
  @ExceptionHandler(InvalidCredentialsException.class)
  public ResponseEntity<ApiResponseDto> handleInvalidCredentialsException(final InvalidCredentialsException ex) {
    ApiResponseDto response = new ApiResponseDto(false, ex.getMessage());
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
  }
  /**
   * Handles the case when a requested Task is not found in the system.
   *
   * @param ex thrown TaskNotFoundException
   * @return ResponseEntity containing a SuccessResponseDTO with success=false and the error message
   */
  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ApiResponseDto> handleNotFound(final NotFoundException ex) {
    ApiResponseDto response = new ApiResponseDto(false, ex.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
  }

  /**
   * Handles validation exceptions caused by invalid input during the processing of request bodies.
   * This handler specifically deals with {@link MethodArgumentNotValidException} thrown when validation fails
   *
   * @param ex the {@link MethodArgumentNotValidException} thrown during validation
   * @return a {@link ResponseEntity} containing a map of validation error details
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handleMethodArgumentNotValidExceptions(final MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();

    ex.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage())
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
  }

}
