package com.example.tasktracker.controllers;

import com.example.tasktracker.dtos.in.TaskRequestDto;
import com.example.tasktracker.dtos.out.TaskResponseDto;
import com.example.tasktracker.entities.Task;
import com.example.tasktracker.entities.TaskStatus;
import com.example.tasktracker.mappers.TaskMapper;
import com.example.tasktracker.services.TaskService;
import com.example.tasktracker.services.UserService;
import com.example.tasktracker.validations.TaskValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    when(userService.getById(1L)).thenReturn(new com.example.tasktracker.entities.User(1L, "test@example.com", "password"));

  }

  /** Test successful task creation */
  @Test
  void createTask_ShouldReturnCreated_WhenTaskIsValid() {
    // Arrange
    doNothing().when(taskValidation).validateTask(any(Task.class));
    when(taskMapper.toEntity(taskRequestDto)).thenReturn(taskEntity);
    when(taskService.createTask(taskEntity, 1L)).thenReturn(taskEntity);
    when(taskMapper.toResponseDto(taskEntity)).thenReturn(taskResponseDto);

    // Act
    ResponseEntity<?> response = taskController.createTask(1L, taskRequestDto);

    // Assert
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(taskResponseDto, response.getBody());
    verify(taskValidation, times(1)).validateTask(taskEntity);
    verify(taskService, times(1)).createTask(taskEntity, 1L);
  }

  /** Test task creation fails validation */
  @Test
  void createTask_ShouldReturnBadRequest_WhenValidationFails() {
    // Arrange
    when(taskMapper.toEntity(taskRequestDto)).thenReturn(taskEntity); // <-- REQUIRED FIX

    doThrow(new IllegalArgumentException("Invalid task"))
            .when(taskValidation).validateTask(any(Task.class));

    // Act
    ResponseEntity<?> response = taskController.createTask(1L, taskRequestDto);

    // Assert
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("Invalid task", response.getBody());
    verify(taskValidation, times(1)).validateTask(any(Task.class));
    verify(taskService, never()).createTask(any(), anyLong());
  }


  /** Test marking task as completed */
  @Test
  void markTaskAsCompleted_ShouldReturnOk_WhenTaskExists() {
    // Arrange
    when(taskService.markTaskAsCompleted(1L, 1L)).thenReturn(taskEntity);
    when(taskMapper.toResponseDto(taskEntity)).thenReturn(taskResponseDto);

    // Act
    ResponseEntity<?> response = taskController.markTaskAsCompleted(1L, 1L);

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(taskResponseDto, response.getBody());
    verify(taskService, times(1)).markTaskAsCompleted(1L, 1L);
  }

  /** Test marking task as completed when task does not exist */
  @Test
  void markTaskAsCompleted_ShouldReturnBadRequest_WhenTaskNotFound() {
    // Arrange
    when(taskService.markTaskAsCompleted(1L, 1L))
            .thenThrow(new IllegalArgumentException("Task not found"));

    // Act
    ResponseEntity<?> response = taskController.markTaskAsCompleted(1L, 1L);

    // Assert
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("Task not found", response.getBody());
    verify(taskService, times(1)).markTaskAsCompleted(1L, 1L);
  }

  /** Test deleting task successfully */
  @Test
  void deleteTask_ShouldReturnOk_WhenTaskDeleted() {
    // Arrange
    when(taskService.deleteTaskByUser(1L, 1L)).thenReturn(true);

    // Act
    ResponseEntity<?> response = taskController.deleteTask(1L, 1L);

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Task deleted successfully", response.getBody());
    verify(taskService, times(1)).deleteTaskByUser(1L, 1L);
  }

  /** Test deleting task fails */
  @Test
  void deleteTask_ShouldReturnNotFound_WhenTaskNotFound() {
    // Arrange
    when(taskService.deleteTaskByUser(1L, 1L)).thenReturn(false);

    // Act
    ResponseEntity<?> response = taskController.deleteTask(1L, 1L);

    // Assert
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Task not found or you are not allowed to delete", response.getBody());
    verify(taskService, times(1)).deleteTaskByUser(1L, 1L);
  }


  /** Test fetching tasks with filters */
  @Test
  void getTasks_ShouldReturnOk_WhenTasksExist() {
    // Arrange
    List<Task> tasks = Collections.singletonList(taskEntity);
    when(taskService.getTasksByUserWithFilters(1L, TaskStatus.PENDING, LocalDate.now().plusDays(1), 0, 10))
            .thenReturn(tasks);
    when(taskMapper.toResponseDto(taskEntity)).thenReturn(taskResponseDto);

    // Act
    ResponseEntity<?> response = taskController.getTasksByUser(
            1L,
            0,
            10,
            "PENDING",
            LocalDate.now().plusDays(1).toString()
    );

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(Collections.singletonList(taskResponseDto), response.getBody());
    verify(taskService, times(1))
            .getTasksByUserWithFilters(1L, TaskStatus.PENDING, LocalDate.now().plusDays(1), 0, 10);
  }
}
