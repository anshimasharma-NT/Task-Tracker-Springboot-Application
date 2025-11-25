package com.example.tasktracker.dtos.out;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link ApiResponseDto}.
 */
public class ApiResponseDtoTest
{
  /**
   * ApiResponseDto .
   */
  private ApiResponseDto apiResponseDto;

  /**
   * Initializes a default SuccessResponseDTO before each test.
   */
  @BeforeEach
  public void setUp() {

    apiResponseDto = new ApiResponseDto(true, "Task is successful");
  }

  /**
   * Tests constructor field initialization.
   */
  @Test
  public void testSuccessResponseDTOConstructor() {
    assertEquals("Task is successful", apiResponseDto.getMessage());
  }

  /**
   * Tests the success flag getter.
   */
  @Test
  public void testSuccess() {

    assertTrue(apiResponseDto.isSuccess());
  }

  /**
   * Tests the message getter.
   */
  @Test
  public void testMessage() {

    assertEquals("Task is successful", apiResponseDto.getMessage());
  }

  /**
   * Tests equals() and hashCode() consistency.
   */
  @Test
  public void testEqualsAndHashCode() {
    ApiResponseDto another = new ApiResponseDto(true, "Task is successful");
    assertEquals(apiResponseDto, another);
    assertEquals(apiResponseDto.hashCode(), another.hashCode());
  }

  /**
   * Tests the toString() output.
   */
  @Test
  public void testToString() {
    String expected = "ApiResponseDto(success=true, message=Task is successful)";
    assertEquals(expected, apiResponseDto.toString());
  }
}
