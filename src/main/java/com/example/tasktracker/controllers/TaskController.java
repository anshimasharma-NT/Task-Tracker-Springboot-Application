package com.example.tasktracker.controllers;

import com.example.tasktracker.constants.NumericConstants;
import com.example.tasktracker.constants.TaskConstants;
import com.example.tasktracker.constants.UrlConstants;
import com.example.tasktracker.dtos.in.TaskRequestDto;
import com.example.tasktracker.dtos.out.PaginatedTaskResponseDto;
import com.example.tasktracker.dtos.out.ApiResponseDto;
import com.example.tasktracker.entities.Task;
import com.example.tasktracker.services.TaskService;
import com.example.tasktracker.validations.TaskValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
/**
 * Controller handling user task-related endpoints.
 */
@RestController
@RequestMapping(UrlConstants.TASK_ENDPOINT)
public class TaskController {

  /**
   * Logger for TaskController.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);

  /**
   * Service for task-related operations.
   */
  @Autowired
  private TaskService taskService;
  /**
   * Validator for validating incoming task creation requests.
   */
  @Autowired
  private TaskValidation taskValidation;

  /**
   * Endpoint for creating a new task.
   * @param taskRequestDto DTO containing task creation details.
   * @return {@link ResponseEntity} with a {@link ApiResponseDto}
   */
  @PostMapping(UrlConstants.ADD_TASK)
  public ResponseEntity<ApiResponseDto> addTask(@RequestBody final TaskRequestDto taskRequestDto) {
    LOGGER.info(TaskConstants.ADD_TASK_MESSAGE, taskRequestDto.getTitle());
    taskValidation.validateAddTask(taskRequestDto);
    ApiResponseDto response = taskService.addTask(taskRequestDto);
    LOGGER.info(TaskConstants.TASK_ADDED_SUCCESS_MESSAGE);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  /**
   * Marks an existing task as completed.
   * @param taskId ID of the task to mark as complete.
   * @param userId ID of the user who created the task.
   * @return ResponseEntity containing success message.
   */
  @PutMapping(UrlConstants.MARK_TASK_AS_COMPLETED)
  public ResponseEntity<ApiResponseDto> markTaskAsComplete(@PathVariable final Long taskId, @PathVariable final Long userId) {
    LOGGER.info(TaskConstants.TASK_MARK_COMPLETE_REQUEST_MESSAGE, taskId, userId);
    Task task = taskValidation.validateTaskID(taskId);
    ApiResponseDto response = taskService.markTaskAsComplete(task);
    LOGGER.info(TaskConstants.TASK_MARK_COMPLETE_SUCCESS_MESSAGE, taskId, userId);
    return  ResponseEntity.status(HttpStatus.OK).body(response);
  }

  /**
   * Deletes a task by its ID.
   * @param taskId ID of the task to delete
   * @param userId ID of the user who created the task.
   * @return ResponseEntity containing success message
   */
  @DeleteMapping(UrlConstants.DELETE_TASK)
  public ResponseEntity<ApiResponseDto> deleteTask(@PathVariable final Long taskId,
                                                   @PathVariable final Long userId) {
    LOGGER.info(TaskConstants.TASK_DELETE_MESSAGE, taskId, userId);
    taskValidation.validateTaskID(taskId);
    ApiResponseDto response = taskService.deleteTask(taskId);
    LOGGER.info(TaskConstants.TASK_DELETE_SUCCESS_MESSAGE, taskId);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  /**
   * Retrieves a paginated and optionally filtered list of tasks for a user.
   *
   * @param userId  the ID of the user whose tasks are being retrieved
   * @param page    the page number for pagination
   * @param size    the size of each page
   * @param dueDate optional filter for task due date in string format
   * @param status  optional filter for task status
   * @return ResponseEntity containing {@link PaginatedTaskResponseDto} with tasks and pagination info
   */
  @GetMapping(UrlConstants.GET_TASKS_BY_USER)
  public ResponseEntity<PaginatedTaskResponseDto> getTasksByUser(
          final @PathVariable Long userId,
          final @RequestParam(defaultValue = NumericConstants.DEFAULT_PAGE_NUMBER) int page,
          final @RequestParam(defaultValue = NumericConstants.DEFAULT_PAGE_SIZE) int size,
          final @RequestParam(required = false) String dueDate,
          final @RequestParam(required = false) String status) {
    LOGGER.info(TaskConstants.TASK_FETCH_REQUEST_MESSAGE, userId, page, size, dueDate, status);
    taskValidation.validateUserID(userId);
    Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
    PaginatedTaskResponseDto response = taskService.getTasksByUser(userId, pageable, dueDate, status);
    LOGGER.info(TaskConstants.TASK_FETCH_SUCCESS_MESSAGE, userId, response.getTasks().size());
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
}
