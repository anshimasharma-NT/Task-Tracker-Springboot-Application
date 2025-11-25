package com.example.tasktracker.dtos.out;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link ApiResponseDto}.
 */
public class SuccessResponseDtoTest {
  /**
   * SuccessResponseDTO .
   */
  private ApiResponseDto successResponse;

  /**
   * Initializes a default SuccessResponseDTO before each test.
   */
  @BeforeEach
  public void setUp() {

    successResponse = new ApiResponseDto(true, "Task is successful");
  }

  /**
   * Tests constructor field initialization.
   */
  @Test
  public void testSuccessResponseDTOConstructor() {
    assertEquals("Task is successful", successResponse.getMessage());
  }

  /**
   * Tests the success flag getter.
   */
  @Test
  public void testSuccess() {

    assertTrue(successResponse.isSuccess());
  }

  /**
   * Tests the message getter.
   */
  @Test
  public void testMessage() {

    assertEquals("Task is successful", successResponse.getMessage());
  }

  /**
   * Tests equals() and hashCode() consistency.
   */
  @Test
  public void testEqualsAndHashCode() {
    ApiResponseDto another = new ApiResponseDto(true, "Task is successful");
    assertEquals(successResponse, another);
    assertEquals(successResponse.hashCode(), another.hashCode());
  }

  /**
   * Tests the toString() output.
   */
  @Test
  public void testToString() {
    String expected = "SuccessResponseDto(success=true, message=Task is successful)";
    assertEquals(expected, successResponse.toString());
  }
}
