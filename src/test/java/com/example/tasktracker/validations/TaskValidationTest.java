package com.example.tasktracker.validations;

import com.example.tasktracker.dtos.in.TaskRequestDto;
import com.example.tasktracker.entities.Task;
import com.example.tasktracker.exceptions.custom.AlreadyExistsException;
import com.example.tasktracker.exceptions.custom.NotFoundException;
import com.example.tasktracker.exceptions.custom.ValidationException;
import com.example.tasktracker.repositories.TaskRepository;
import com.example.tasktracker.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link TaskValidation}.
 */
@ExtendWith(MockitoExtension.class)
class TaskValidationTest {

  /** Mocked UserRepository for user-related database operations. */
  @Mock
  private UserRepository userRepository;

  /** Mocked TaskRepository for task-related database operations. */
  @Mock
  private TaskRepository taskRepository;

  /** The TaskRequestValidator instance with mocks injected. */
  @InjectMocks
  private TaskValidation taskValidation;

  /** Sample DTO representing a task input for testing. */
  private TaskRequestDto taskRequestDto;

  /**
   * Setup method to initialize test data before each test.
   */
  @BeforeEach
  void setup() {
    taskRequestDto = new TaskRequestDto();
    taskRequestDto.setTitle("Task title");
    taskRequestDto.setUserId(1L);
    taskRequestDto.setDescription("Task description");
    taskRequestDto.setDueDate("11-29-2025");
  }

  /**
   * Tests successful validation when task does not already exist.
   */
  @Test
  void testValidateCreateTaskSuccess() {
    when(taskRepository.existsByUserIdAndTitleIgnoreCase(taskRequestDto.getUserId(), taskRequestDto.getTitle()))
            .thenReturn(false);

    assertDoesNotThrow(() -> taskValidation.validateAddTask(taskRequestDto));

    verify(taskRepository, times(1))
            .existsByUserIdAndTitleIgnoreCase(taskRequestDto.getUserId(), taskRequestDto.getTitle());
  }

  /**
   * Tests validation failure when task with same title already exists.
   */
  @Test
  void testValidateCreateTaskAlreadyExists() {
    when(taskRepository.existsByUserIdAndTitleIgnoreCase(taskRequestDto.getUserId(), taskRequestDto.getTitle()))
            .thenReturn(true);

    assertThrows(AlreadyExistsException.class,
            () -> taskValidation.validateAddTask(taskRequestDto));

    verify(taskRepository, times(1))
            .existsByUserIdAndTitleIgnoreCase(taskRequestDto.getUserId(), taskRequestDto.getTitle());
  }

  /**
   * Tests successful validation of task ID when task exists.
   */
  @Test
  void testValidateTaskIDSuccess() {
    Task task = new Task();
    task.setTaskId(1L);

    when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

    Task result = assertDoesNotThrow(() -> taskValidation.validateTaskID(1L));
    assert (result.getTaskId().equals(1L));

    verify(taskRepository, times(1)).findById(1L);
  }

  /**
   * Tests validation failure of task ID when task does not exist.
   */
  @Test
  void testValidateTaskIDNotFound() {
    when(taskRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(ValidationException.class, () -> taskValidation.validateTaskID(1L));

    verify(taskRepository, times(1)).findById(1L);
  }

  /**
   * Tests successful validation of user ID when user exists.
   */
  @Test
  void testValidateUserIDSuccess() {
    when(userRepository.existsByUserId(1L)).thenReturn(true);

    assertDoesNotThrow(() -> taskValidation.validateUserID(1L));

    verify(userRepository, times(1)).existsByUserId(1L);
  }

  /**
   * Tests validation failure of user ID when user does not exist.
   */
  @Test
  void testValidateUserIDNotFound() {
    when(userRepository.existsByUserId(1L)).thenReturn(false);

    assertThrows(NotFoundException.class, () -> taskValidation.validateUserID(1L));

    verify(userRepository, times(1)).existsByUserId(1L);
  }
}
