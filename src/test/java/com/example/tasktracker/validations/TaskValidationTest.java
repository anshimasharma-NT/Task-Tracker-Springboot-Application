package com.example.tasktracker.validations;

import com.example.tasktracker.constants.NumericConstants;
import com.example.tasktracker.entities.Task;
import com.example.tasktracker.entities.TaskStatus;
import com.example.tasktracker.exceptions.custom.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TaskValidationTest {

  private TaskValidation taskValidation;

  @BeforeEach
  void setUp() {
    taskValidation = new TaskValidation();
  }

  // -------------------------------------------------------------
  // validateTask() tests
  // -------------------------------------------------------------

  @Test
  void validateTask_ShouldThrow_WhenTaskIsNull() {
    assertThrows(ValidationException.class,
            () -> taskValidation.validateTask(null));
  }

  @Test
  void validateTask_ShouldThrow_WhenTitleMissing() {
    Task task = new Task();
    task.setTitle("");
    task.setUserId(1L);

    assertThrows(ValidationException.class,
            () -> taskValidation.validateTask(task));
  }

  @Test
  void validateTask_ShouldThrow_WhenTitleTooLong() {
    Task task = new Task();
    task.setTitle("A".repeat(NumericConstants.TITLE_LENGTH + 1));
    task.setUserId(1L);

    assertThrows(ValidationException.class,
            () -> taskValidation.validateTask(task));
  }

  @Test
  void validateTask_ShouldThrow_WhenDescriptionTooLong() {
    Task task = new Task();
    task.setTitle("Valid Title");
    task.setDescription("A".repeat(NumericConstants.DESCRIPTION_LENGTH + 1));
    task.setUserId(1L);

    assertThrows(ValidationException.class,
            () -> taskValidation.validateTask(task));
  }

  @Test
  void validateTask_ShouldThrow_WhenDueDateInPast() {
    Task task = new Task();
    task.setTitle("Valid Title");
    task.setUserId(1L);
    task.setDueDate(LocalDate.now().minusDays(1));

    assertThrows(ValidationException.class,
            () -> taskValidation.validateTask(task));
  }

  @Test
  void validateTask_ShouldThrow_WhenUserIdInvalid() {
    Task task = new Task();
    task.setTitle("Valid Title");
    task.setUserId(0L);

    assertThrows(ValidationException.class,
            () -> taskValidation.validateTask(task));
  }

  @Test
  void validateTask_ShouldPass_WhenValid() {
    Task task = new Task();
    task.setTitle("Valid Title");
    task.setDescription("Short description");
    task.setUserId(1L);
    task.setDueDate(LocalDate.now().plusDays(1));

    assertDoesNotThrow(() -> taskValidation.validateTask(task));
  }

  // -------------------------------------------------------------
  // validateTaskFilters() tests
  // -------------------------------------------------------------

  @Test
  void validateTaskFilters_ShouldReturnValidParams() {
    TaskValidation.FilterParams params =
            taskValidation.validateTaskFilters("PENDING", "2030-01-01", 1, 10);

    assertEquals(TaskStatus.PENDING, params.getStatus());
    assertEquals(LocalDate.of(2030, 1, 1), params.getDueDate());
    assertEquals(1, params.getPage());
    assertEquals(10, params.getSize());
  }

  @Test
  void validateTaskFilters_ShouldThrow_WhenInvalidStatus() {
    assertThrows(ValidationException.class,
            () -> taskValidation.validateTaskFilters("WRONG", null, 0, 10));
  }

  @Test
  void validateTaskFilters_ShouldThrow_WhenInvalidDueDate() {
    assertThrows(ValidationException.class,
            () -> taskValidation.validateTaskFilters("PENDING", "wrong-date", 0, 10));
  }

  @Test
  void validateTaskFilters_ShouldDefaultPage_WhenNegative() {
    TaskValidation.FilterParams params =
            taskValidation.validateTaskFilters("PENDING", null, -5, 10);

    assertEquals(0, params.getPage());
  }

  @Test
  void validateTaskFilters_ShouldDefaultSize_WhenZeroOrNegative() {
    TaskValidation.FilterParams params =
            taskValidation.validateTaskFilters("PENDING", null, 0, -10);

    assertEquals(NumericConstants.PAGINATION_SIZE, params.getSize());
  }

  @Test
  void validateTaskFilters_ShouldCapSize_WhenTooLarge() {
    int tooLarge = NumericConstants.MAX_PAGE_SIZE + 100;

    TaskValidation.FilterParams params =
            taskValidation.validateTaskFilters("PENDING", null, 0, tooLarge);

    assertEquals(NumericConstants.MAX_PAGE_SIZE, params.getSize());
  }

  @Test
  void validateTaskFilters_ShouldHandleNullInputs() {
    TaskValidation.FilterParams params =
            taskValidation.validateTaskFilters(null, null, 0, 10);

    assertNull(params.getStatus());
    assertNull(params.getDueDate());
    assertEquals(0, params.getPage());
    assertEquals(10, params.getSize());
  }
}
