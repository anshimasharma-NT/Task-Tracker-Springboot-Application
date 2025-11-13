package com.example.tasktracker.controllers;

import com.example.tasktracker.constants.UrlConstants;
import com.example.tasktracker.dtos.in.TaskRequestDto;
import com.example.tasktracker.dtos.out.TaskResponseDto;
import com.example.tasktracker.entities.Task;
import com.example.tasktracker.entities.User;
import com.example.tasktracker.mappers.TaskMapper;
import com.example.tasktracker.services.TaskService;
import com.example.tasktracker.services.UserService;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


/**
 * Controller responsible for handling task-related operations.
 *
 * <p>Provides endpoints for creating and retrieving user tasks.</p>
 */
@RestController
@RequestMapping(UrlConstants.TASK)
public class TaskController {

  /** Logger instance. */
  private static final Logger LOGGER = LoggerFactory
          .getLogger(TaskController.class);

  /** Task Service. **/
  @Autowired
  private TaskService taskService;

  /** Task Mapper. **/
  @Autowired
  private TaskMapper taskMapper;

  /** User Service. **/
  @Autowired
  private UserService userService;


  /**
   * Creates a new task for a specific user.
   *
   * @param userId the ID of the user creating the task
   * @param taskDto the task data from the request body
   * @return {@link ResponseEntity} containing the created task or an error message
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
      Task savedTask = taskService.createTask(task, userId);

      TaskResponseDto responseDto = taskMapper.toResponseDto(savedTask);

      LOGGER.info("Task '{}' created for user {}", responseDto.getTitle(), user.getEmail());
      return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);

    } catch (Exception e) {
      LOGGER.error("Error creating task", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("Error creating task: " + e.getMessage());
    }
  }

  /**
   * Retrieves all tasks for a specific user.
   *
   * @param userId the ID of the user whose tasks are requested
   * @return {@link ResponseEntity} containing the list of user tasks
   */
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


  /**
   * Marks a task as completed for a specific user.
   *
   * @param taskId the ID of the task to mark as completed
   * @param userId the ID of the user who owns the task
   * @return a {@link ResponseEntity} containing the updated task or an error message
   */
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

  /**
   * Deletes a task belonging to a specific user.
   *
   * @param userId the ID of the user requesting the deletion
   * @param taskId the ID of the task to delete
   * @return a {@link ResponseEntity} with a success or error message
   */
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
