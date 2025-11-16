package com.example.tasktracker.controllers;

import com.example.tasktracker.dtos.in.TaskRequestDto;
import com.example.tasktracker.dtos.out.TaskResponseDto;
import com.example.tasktracker.entities.Task;
import com.example.tasktracker.entities.TaskStatus;
import com.example.tasktracker.entities.User;
import com.example.tasktracker.exceptions.custom.TaskNotFoundException;
import com.example.tasktracker.exceptions.custom.UserNotFoundException;
import com.example.tasktracker.exceptions.custom.ValidationException;
import com.example.tasktracker.mappers.TaskMapper;
import com.example.tasktracker.services.TaskService;
import com.example.tasktracker.services.UserService;
import com.example.tasktracker.validations.TaskValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskControllerTest {

  @InjectMocks
  private TaskController taskController;

  @Mock
  private TaskService taskService;

  @Mock
  private TaskValidation taskValidation;

  @Mock
  private TaskMapper taskMapper;

  @Mock
  private UserService userService;

  private TaskRequestDto taskRequestDto;
  private Task taskEntity;
  private TaskResponseDto taskResponseDto;
  private User user;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    taskRequestDto = new TaskRequestDto("Test Task", "Description", LocalDate.now().plusDays(1));

    taskEntity = Task.builder()
            .id(1L)
            .title("Test Task")
            .description("Description")
            .dueDate(LocalDate.now().plusDays(1))
            .status(TaskStatus.PENDING)
            .userId(1L)
            .build();

    taskResponseDto = new TaskResponseDto(
            1L,
            "Test Task",
            "Description",
            LocalDate.now().plusDays(1),
            TaskStatus.PENDING,
            LocalDateTime.now(),
            1L
    );

    user = new User(1L, "Anshima Sharma", "test@example.com", "password");
    when(userService.getById(1L)).thenReturn(user);
  }

  // ---------------- CREATE TASK ----------------
  @Test
  void createTask_ShouldReturnCreated_WhenTaskIsValid() {
    doNothing().when(taskValidation).validateTask(any(Task.class));
    when(taskMapper.toEntity(taskRequestDto)).thenReturn(taskEntity);
    when(taskService.createTask(taskEntity, 1L)).thenReturn(taskEntity);
    when(taskMapper.toResponseDto(taskEntity)).thenReturn(taskResponseDto);

    var response = taskController.createTask(1L, taskRequestDto);

    assertEquals(201, response.getStatusCodeValue());
    assertEquals(taskResponseDto, response.getBody());
  }

  @Test
  void createTask_ShouldThrowValidationException_WhenInvalid() {
    when(taskMapper.toEntity(taskRequestDto)).thenReturn(taskEntity);
    doThrow(new ValidationException("Invalid task"))
            .when(taskValidation).validateTask(any(Task.class));

    ValidationException ex = assertThrows(
            ValidationException.class,
            () -> taskController.createTask(1L, taskRequestDto)
    );

    assertEquals("Invalid task", ex.getMessage());
    verify(taskService, never()).createTask(any(), anyLong());
  }

  @Test
  void createTask_ShouldThrowUserNotFound_WhenUserMissing() {
    when(userService.getById(1L)).thenReturn(null);

    assertThrows(
            UserNotFoundException.class,
            () -> taskController.createTask(1L, taskRequestDto)
    );
  }

  // ---------------- MARK COMPLETE ----------------
  @Test
  void markTaskAsCompleted_ShouldReturnOk_WhenTaskExists() {
    // Mock the controller's getTaskByIdAndUser
    when(taskService.getTaskByIdAndUser(1L, 1L)).thenReturn(taskEntity);
    when(taskService.markTaskAsCompleted(1L, 1L)).thenReturn(taskEntity);
    when(taskMapper.toResponseDto(taskEntity)).thenReturn(taskResponseDto);

    var response = taskController.markTaskAsCompleted(1L, 1L);

    assertEquals(200, response.getStatusCodeValue());
    assertEquals(taskResponseDto, response.getBody());
  }

  @Test
  void markTaskAsCompleted_ShouldThrowTaskNotFound_WhenMissing() {
    when(taskService.getTaskByIdAndUser(1L, 1L)).thenReturn(null);

    TaskNotFoundException ex = assertThrows(
            TaskNotFoundException.class,
            () -> taskController.markTaskAsCompleted(1L, 1L)
    );

    assertEquals("Error updating task: Task not found or not owned by user.", ex.getMessage());
  }

  // ---------------- DELETE TASK ----------------
  @Test
  void deleteTask_ShouldReturnOk_WhenTaskDeleted() {
    when(taskService.deleteTaskByUser(1L, 1L)).thenReturn(true);

    var response = taskController.deleteTask(1L, 1L);

    assertEquals(200, response.getStatusCodeValue());
    assertEquals("Task deleted successfully", response.getBody());
  }

  @Test
  void deleteTask_ShouldThrowTaskNotFound_WhenNotDeleted() {
    when(taskService.deleteTaskByUser(1L, 1L)).thenReturn(false);

    assertThrows(
            TaskNotFoundException.class,
            () -> taskController.deleteTask(1L, 1L)
    );
  }

  // ---------------- GET TASKS ----------------
  @Test
  void getTasks_ShouldReturnOk_WhenTasksExist() {
    when(taskService.getTasksByUserWithFilters(1L, TaskStatus.PENDING, LocalDate.now().plusDays(1), 0, 10))
            .thenReturn(Collections.singletonList(taskEntity));
    when(taskMapper.toResponseDto(taskEntity)).thenReturn(taskResponseDto);

    var response = taskController.getTasksByUser(
            1L,
            0,
            10,
            "PENDING",
            LocalDate.now().plusDays(1).toString()
    );

    assertEquals(200, response.getStatusCodeValue());
    assertEquals(Collections.singletonList(taskResponseDto), response.getBody());
  }
}
