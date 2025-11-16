package com.example.tasktracker.validations;

import com.example.tasktracker.constants.NumericConstants;
import com.example.tasktracker.entities.Task;
import com.example.tasktracker.entities.TaskStatus;
import com.example.tasktracker.exceptions.custom.ValidationException;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

/**
 * Validation utility for Task entity operations and filter parameters.
 * Ensures that task data meets required constraints before persisting
 * and validates filters used for task retrieval.
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
      throw new ValidationException("Task cannot be null.");
    }

    // Validate title
    if (task.getTitle() == null || task.getTitle().trim().isEmpty()) {
      throw new ValidationException("Task title cannot be empty.");
    }
    if (task.getTitle().length() > NumericConstants.TITLE_LENGTH) {
      throw new ValidationException("Task title cannot exceed 150 characters.");
    }

    // Validate description
    if (task.getDescription() != null && task.getDescription().length() > NumericConstants.DESCRIPTION_LENGTH) {
      throw new ValidationException("Task description cannot exceed 500 characters.");
    }

    // Validate due date
    if (task.getDueDate() != null && task.getDueDate().isBefore(LocalDate.now())) {
      throw new ValidationException("Due date cannot be in the past.");
    }

    // Validate userId
    if (task.getUserId() == null || task.getUserId() <= 0) {
      throw new ValidationException("Task must be associated with a valid user ID.");
    }
  }

  /**
   * Validates filter parameters for fetching tasks.
   *
   * @param statusStr the status string (PENDING, COMPLETED, etc.)
   * @param dueDateStr the due date string in yyyy-MM-dd format
   * @param page page number (0-based)
   * @param size page size
   * @return validated FilterParams object containing parsed values
   * @throws IllegalArgumentException if any parameter is invalid
   */
  public FilterParams validateTaskFilters(final String statusStr, final String dueDateStr, final int page, final int size) {
    TaskStatus status = null;
    LocalDate dueDate = null;

    // Validate status
    if (statusStr != null && !statusStr.isEmpty()) {
      try {
        status = TaskStatus.valueOf(statusStr.toUpperCase());
      } catch (ValidationException e) {
        throw new ValidationException(
                "Invalid status value: " + statusStr + ". Allowed values: " + Arrays.toString(TaskStatus.values())
        );
      }
    }

    // Validate due date
    if (dueDateStr != null && !dueDateStr.isEmpty()) {
      try {
        dueDate = LocalDate.parse(dueDateStr);
      } catch (DateTimeParseException e) {
        throw new IllegalArgumentException(
                "Invalid dueDate format: " + dueDateStr + ". Expected format: yyyy-MM-dd"
        );
      }
    }

    // Use local variables for page and size if they're final
    int validatedPage = page < 0 ? 0 : page;
    int validatedSize = (size <= 0) ? NumericConstants.PAGINATION_SIZE : size;
    if (validatedSize > NumericConstants.MAX_PAGE_SIZE) {
      validatedSize = NumericConstants.MAX_PAGE_SIZE;
    }

    return new FilterParams(status, dueDate, validatedPage, validatedSize);
  }


  /**
   * Helper class to encapsulate validated filter parameters.
   */
  @Getter
  public static class FilterParams {
    /** status. */
    private final TaskStatus status;

    /** dueDate. */
    private final LocalDate dueDate;

    /** page. */
    private final int page;

    /** size. */
    private final int size;

    /** filter params.
     * @param status the user's task status (pending or completed)
     * @param page pagination used
     * @param dueDate the task's duedate
     * @param size size of the task's to be displayed
     */
    public FilterParams(final TaskStatus status, final LocalDate dueDate, final int page, final int size) {
      this.status = status;
      this.dueDate = dueDate;
      this.page = page;
      this.size = size;
    }

  }
}
