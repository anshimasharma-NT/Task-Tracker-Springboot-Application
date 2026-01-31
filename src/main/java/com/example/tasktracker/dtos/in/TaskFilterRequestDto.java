package com.example.tasktracker.dtos.in;


import com.example.tasktracker.constants.NumericConstants;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;


/**
 * DTO for filtering and paginating user tasks.
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class TaskFilterRequestDto
{

  /**
   * ID of the user whose tasks are being retrieved.
   */
  @NotNull(message = "User ID is required")
  private Long userId;
  /**
   * Page number (0-based index).
   */
  @Pattern(
          regexp = "^[0-9]+$",
          message = "Page number must be a non-negative integer"
  )
  private String page = NumericConstants.DEFAULT_PAGE_NUMBER;
  /**
   * Number of records per page.
   */
  @Pattern(
          regexp = "^[1-9][0-9]*$",
          message = "Page size must be at least 1"
  )
  private String size = NumericConstants.DEFAULT_PAGE_SIZE;
  /**
   * Due date filter in format "MM-dd-uuuu".
   */
  @Pattern(
          regexp = "^(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])-[0-9]{4}$",
          message = "Due date must be in format MM-dd-yyyy"
  )
  private String dueDate;
  /**
   * Task status filter (PENDING or COMPLETE).
   */
  @Pattern(
          regexp = "^(PENDING|COMPLETED)$",
          message = "Status must be either PENDING or COMPLETED"
  )
  private String status;
}
