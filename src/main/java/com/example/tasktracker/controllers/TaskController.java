package com.example.tasktracker.controllers;

import com.example.tasktracker.constants.UrlConstants;
import com.example.tasktracker.dtos.in.TaskRequestDto;
import com.example.tasktracker.dtos.out.TaskResponseDto;
import com.example.tasktracker.entities.Task;
import com.example.tasktracker.entities.User;
import com.example.tasktracker.mappers.TaskMapper;
import com.example.tasktracker.services.TaskService;
import com.example.tasktracker.services.UserService;
import com.example.tasktracker.validations.TaskValidation;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping(UrlConstants.TASK)
public class TaskController {

  private static final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);

  @Autowired
  private TaskService taskService;

  @Autowired
  private TaskMapper taskMapper;

  @Autowired
  private UserService userService;

  @Autowired
  private TaskValidation taskValidation;

  /**
   * Creates a new task for a specific user.
   */
  @PostMapping(UrlConstants.ADD_TASK)
  public ResponseEntity<?> addTask(
          @PathVariable final Long userId,
          @RequestBody final TaskRequestDto taskDto) {
    try {
      User user = userService.getById(userId);
      if (user == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("User not found with ID: " + userId);
      }

      Task task = taskMapper.toEntity(taskDto);
      task.setUserId(userId);

      taskValidation.validateTask(task);

      Task savedTask = taskService.createTask(task, userId);
      TaskResponseDto responseDto = taskMapper.toResponseDto(savedTask);

      LOGGER.info("Task '{}' created for user {}", responseDto.getTitle(), user.getEmail());
      return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);

    } catch (IllegalArgumentException e) {
      LOGGER.warn("Validation failed while creating task: {}", e.getMessage());
      return ResponseEntity.badRequest().body(e.getMessage());
    } catch (Exception e) {
      LOGGER.error("Error creating task", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("Error creating task: " + e.getMessage());
    }
  }

  @GetMapping(UrlConstants.GET_TASKS_LIST)
  public ResponseEntity<?> getTasksByUser(@PathVariable final Long userId) {
    try {
      User user = userService.getById(userId);
      if (user == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("User not found with ID: " + userId);
      }

      List<Task> tasks = taskService.getTasksByUser(userId);
      List<TaskResponseDto> responseDtos = tasks.stream()
              .map(taskMapper::toResponseDto)
              .collect(Collectors.toList());

      return ResponseEntity.ok(responseDtos);
    } catch (Exception e) {
      LOGGER.error("Error retrieving tasks", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("Error retrieving tasks: " + e.getMessage());
    }
  }

  @PutMapping(UrlConstants.COMPLETE_TASK)
  public ResponseEntity<?> markTaskAsCompleted(
          final @PathVariable Long taskId,
          final @PathVariable Long userId) {
    try {
      Task updatedTask = taskService.markTaskAsCompleted(taskId, userId);
      return ResponseEntity.ok(taskMapper.toResponseDto(updatedTask));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("Error updating task: " + e.getMessage());
    }
  }

  @DeleteMapping(UrlConstants.DELETE_TASK)
  public ResponseEntity<String> deleteTask(
          @PathVariable final Long userId,
          @PathVariable final Long taskId) {
    try {
      boolean deleted = taskService.deleteTaskByUser(userId, taskId);
      if (deleted) {
        return ResponseEntity.ok("Task deleted successfully");
      }
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
              .body("Task not found or not owned by user");
    } catch (Exception e) {
      LOGGER.error("Error deleting task", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("Error deleting task: " + e.getMessage());
    }
  }
}
