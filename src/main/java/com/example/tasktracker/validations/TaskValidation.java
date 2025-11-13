package com.example.tasktracker.validations;

import com.example.tasktracker.entities.Task;
import java.time.LocalDate;
import org.springframework.stereotype.Component;

/**
 * Validation utility for Task entity operations.
 * Ensures that task data meets required constraints before persisting.
 */
@Component
public class TaskValidation {

  /**
   * Validates a task before creation or update.
   *
   * @param task Task object to validate
   * @throws IllegalArgumentException if validation fails
   */
  public void validateTask(final Task task) {
    if (task == null) {
      throw new IllegalArgumentException("Task cannot be null.");
    }

    // Validate title
    if (task.getTitle() == null || task.getTitle().trim().isEmpty()) {
      throw new IllegalArgumentException("Task title cannot be empty.");
    }
    if (task.getTitle().length() > 150) {
      throw new IllegalArgumentException("Task title cannot exceed 150 characters.");
    }

    // Validate description
    if (task.getDescription() != null && task.getDescription().length() > 500) {
      throw new IllegalArgumentException("Task description cannot exceed 500 characters.");
    }

    // Validate due date
    if (task.getDueDate() != null && task.getDueDate().isBefore(LocalDate.now())) {
      throw new IllegalArgumentException("Due date cannot be in the past.");
    }

    // Validate userId
    if (task.getUserId() == null || task.getUserId() <= 0) {
      throw new IllegalArgumentException("Task must be associated with a valid user ID.");
    }
  }
}
