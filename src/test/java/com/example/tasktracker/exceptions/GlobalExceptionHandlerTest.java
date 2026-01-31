package com.example.tasktracker.exceptions;

import com.example.tasktracker.dtos.out.ApiResponseDto;
import com.example.tasktracker.exceptions.custom.AlreadyExistsException;
import com.example.tasktracker.exceptions.custom.InvalidCredentialsException;
import com.example.tasktracker.exceptions.custom.NotFoundException;
import com.example.tasktracker.exceptions.custom.ValidationException;
import com.example.tasktracker.exceptions.handler.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Handles application-wide exceptions.
 */
class GlobalExceptionHandlerTest {
  /**
   * The instance of GlobalExceptionHandler being tested.
   */
  private final GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

  /**.
   * Tests the behavior of the {@code handleEmailExists} method.
   */
  @Test
  public void testHandleEmailExists() {
    AlreadyExistsException alreadyExistsException = new AlreadyExistsException("Email already exists");
    ResponseEntity<ApiResponseDto> response =
            exceptionHandler.handleEmailExists(alreadyExistsException);
    assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    assertNotNull(response.getBody());
    assertFalse(response.getBody().isSuccess());
    assertEquals("Email already exists", response.getBody().getMessage());

  }
  /**.
   * Tests the behavior of the {@code handleValidationException} method.
   */
  @Test
  public void testHandleValidationException() {
    ValidationException validationException = new ValidationException("Request contains invalid fields");
    ResponseEntity<ApiResponseDto> response =
            exceptionHandler.handleValidationException(validationException);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertNotNull(response.getBody());
    assertFalse(response.getBody().isSuccess());
    assertEquals("Request contains invalid fields", response.getBody().getMessage());
  }
  /**.
   * Tests the behavior of the {@code handleInvalidCredentialsException} method.
   */
  @Test
  public void testInvalidCredentialsException() {
    InvalidCredentialsException invalidCredentialsException = new InvalidCredentialsException("Invalid credentials");
    ResponseEntity<ApiResponseDto> response =
            exceptionHandler.handleInvalidCredentialsException(invalidCredentialsException);
    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    assertNotNull(response.getBody());
    assertFalse(response.getBody().isSuccess());
    assertEquals("Invalid credentials", response.getBody().getMessage());
  }
  /**.
   * Tests the behavior of the {@code handleNotFoundException} method.
   */
  @Test
  public void testNotFoundException() {
    NotFoundException notFoundException = new NotFoundException("Not found exception");
    ResponseEntity<ApiResponseDto> response =
            exceptionHandler.handleNotFound(notFoundException);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertNotNull(response.getBody());
    assertFalse(response.getBody().isSuccess());
    assertEquals("Not found exception", response.getBody().getMessage());
  }
  /**.
   * Tests the behavior of the {@code handleMethodArgumentNotValidExceptions} method.
   */
  @Test
  public void testHandleMethodArgumentNotValidExceptions() {
    BindingResult bindingResult = mock(BindingResult.class);
    MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
    when(exception.getBindingResult()).thenReturn(bindingResult);
    when(bindingResult.getFieldErrors()).thenReturn(List.of(
            new FieldError("user", "email", "Email is required"),
            new FieldError("user", "password", "Password must be at least 8 characters")
    ));
    ResponseEntity<?> response = exceptionHandler.handleMethodArgumentNotValidExceptions(exception);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    Map<String, String> errors = (Map<String, String>) response.getBody();
    assertNotNull(errors);
    assertEquals(2, errors.size());
    assertEquals("Email is required", errors.get("email"));
    assertEquals("Password must be at least 8 characters", errors.get("password"));
  }
}
