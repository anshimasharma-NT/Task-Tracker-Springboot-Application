package com.example.tasktracker.dtos.in;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Data Transfer Object for creating or updating a task.
 *
 * <p>This DTO is used for client input and does not expose internal fields such as ID or timestamps.</p>
 */

@Getter
@Setter
@ToString
@EqualsAndHashCode
public final class TaskRequestDto {

  /**
   * Task Title.
   */
  @NotBlank(message = "title is required")
  private String title;
  /**
   * Task Description.
   */
  private String description;
  /**
   * Each task belongs to a single user.
   */
  @NotNull(message = "User ID is required")
  private Long  userId;
  /**
   * Task DueDate.
   */
  @Pattern(
          regexp = "^(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])-[0-9]{4}$",
          message = "Due date must be in format MM-dd-yyyy"
  )
  private String dueDate;

}
