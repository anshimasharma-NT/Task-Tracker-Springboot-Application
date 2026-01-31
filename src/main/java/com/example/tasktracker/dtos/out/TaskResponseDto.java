package com.example.tasktracker.dtos.out;

import com.example.tasktracker.entities.TaskStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Data Transfer Object for returning task details in API responses.
 *
 * <p>This DTO hides sensitive or unnecessary entity fields
 * and provides a clear, structured representation of a task.</p>
 */
@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public final class TaskResponseDto
{

  /**
   * Unique identifier of the task.
   */
  private Long taskId;

  /**
   * Title of the task.
   */
  private String title;

  /**
   * Description of the task.
   */
  private String description;

  /**
   * Status of the task.
   */
  private TaskStatus status;

  /**
   * Due date of the task.
   */
  private LocalDate dueDate;

  /**
   * Timestamp when the task was created.
   */
  private LocalDateTime createdAt;

  public TaskResponseDto()
  {

  }
}

