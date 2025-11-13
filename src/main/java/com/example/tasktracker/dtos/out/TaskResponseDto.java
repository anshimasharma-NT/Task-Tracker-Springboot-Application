package com.example.tasktracker.dtos.out;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for returning task details in API responses.
 *
 * <p>This DTO hides sensitive or unnecessary entity fields
 * and provides a clear, structured representation of a task.</p>
 */

@Getter
@Setter
public final class TaskResponseDto
{

  /** Unique identifier of the user. */
  private final Long id;

  /** Name of the user. */
  private final String title;

  /** Email address of the user. */
  private final String description;

  /** DueDate of the task. **/
  private final LocalDate dueDate;

  /** Task status. **/
  private final String status;

  /** Task creation time. **/
  private final LocalDateTime createdAt;

  /** User id of the user. **/
  private final Long userId;

  /**
 * Constructs a new {@code TaskResponseOutDto} instance with the provided task details.
   * @param id   the unique identifier of the task
   * @param title   the unique title of the task
   * @param description  the description of the title of the task
   * @param dueDate  the due date of the task
   * @param status  the status of the task
   * @param createdAt   the task creation time
   * @param userId    user id who owns this task
 * **/
  public TaskResponseDto(final Long id, final String title, final String description,
                         final LocalDate dueDate, final String status,
                         final LocalDateTime createdAt, final Long userId) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.dueDate = dueDate;
    this.status = status;
    this.createdAt = createdAt;
    this.userId = userId;
  }


}
