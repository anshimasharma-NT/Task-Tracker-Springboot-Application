package com.example.tasktracker.dtos.in;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Data Transfer Object for creating or updating a task.
 *
 * <p>This DTO is used for client input and does not expose internal fields such as ID or timestamps.</p>
 */

@Getter
@Setter
public final class TaskRequestDto
{

  /** Title of the task. */
  @NotBlank(message = "Title is required")
  private String title;

  /** Detailed description of the task (optional). */
  private String description;

  /** The date by which the task should be completed. */
  @NotNull(message = "Due date is required")
  private LocalDate dueDate;

  /** user ID to whom the task will belong. */
  private Long userId;

}
