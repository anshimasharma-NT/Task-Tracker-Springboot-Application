package com.example.tasktracker.controllers;

import com.example.tasktracker.constants.SuccessConstants;
import com.example.tasktracker.constants.UrlConstants;
import com.example.tasktracker.dtos.in.TaskRequestDto;
import com.example.tasktracker.dtos.out.TaskResponseDto;
import com.example.tasktracker.entities.Task;
import com.example.tasktracker.entities.TaskStatus;
import com.example.tasktracker.entities.User;
import com.example.tasktracker.exceptions.custom.TaskAlreadyCompletedException;
import com.example.tasktracker.exceptions.custom.TaskNotFoundException;
import com.example.tasktracker.exceptions.custom.UserNotFoundException;
import com.example.tasktracker.exceptions.custom.ValidationException;
import com.example.tasktracker.mappers.TaskMapper;
import com.example.tasktracker.services.TaskService;
import com.example.tasktracker.services.UserService;
import com.example.tasktracker.validations.TaskValidation;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.tasktracker.constants.ErrorConstants.*;

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

  @PostMapping(UrlConstants.ADD_TASK)
  public ResponseEntity<TaskResponseDto> createTask(
          @PathVariable final Long userId,
          @RequestBody final TaskRequestDto taskDto) {

    User user = userService.getById(userId);
    if (user == null) {
      throw new UserNotFoundException(ERROR_USER_NOT_FOUND + userId);
    }

    Task task = taskMapper.toEntity(taskDto);
    task.setUserId(userId);

    taskValidation.validateTask(task);

    Task savedTask = taskService.createTask(task, userId);
    if(savedTask == null){
      throw new TaskNotFoundException(ERROR_CREATE_TASK);
    }
    TaskResponseDto responseDto = taskMapper.toResponseDto(savedTask);
    LOGGER.info(SuccessConstants.TASK_CREATE_SUCCESS, responseDto.getTitle(), user.getEmail());
    return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
  }

  @GetMapping(UrlConstants.GET_TASKS_LIST)
  public ResponseEntity<List<TaskResponseDto>> getTasksByUser(
          @PathVariable final Long userId,
          @RequestParam(value = "page", defaultValue = "0") final int page,
          @RequestParam(value = "size", defaultValue = "10") final int size,
          @RequestParam(value = "status", required = false) final String statusStr,
          @RequestParam(value = "dueDate", required = false) final String dueDateStr
  ) {

    User user = userService.getById(userId);
    if (user == null) {
      throw new UserNotFoundException(ERROR_USER_NOT_FOUND + userId);
    }

    TaskStatus status = null;
    if (statusStr != null && !statusStr.isEmpty()) {
      try {
        status = TaskStatus.valueOf(statusStr.toUpperCase());
      } catch (IllegalArgumentException e) {
        throw new ValidationException(ERROR_INVALID_TASK_STATUS + statusStr);
      }
    }

    LocalDate dueDate = null;
    if (dueDateStr != null && !dueDateStr.isEmpty()) {
      dueDate = LocalDate.parse(dueDateStr);
    }

    List<Task> tasks = taskService.getTasksByUserWithFilters(userId, status, dueDate, page, size);
    if(tasks == null) {
      throw new TaskNotFoundException(ERROR_RETRIEVE_TASKS);
    }
    List<TaskResponseDto> responseDtos = tasks.stream()
            .map(taskMapper::toResponseDto)
            .collect(Collectors.toList());


    return ResponseEntity.ok(responseDtos);
  }

  @PutMapping(UrlConstants.COMPLETE_TASK)
  public ResponseEntity<TaskResponseDto> markTaskAsCompleted(
          @PathVariable Long taskId,
          @PathVariable Long userId) {

    Task task = taskService.getTaskByIdAndUser(taskId, userId); // fetch the task first
    if (task == null) {
      throw new TaskNotFoundException(ERROR_UPDATE_TASK);
    }
    if (task.getStatus() == TaskStatus.COMPLETED) {
      throw new TaskAlreadyCompletedException("Task status is already completed");
    }

    Task updatedTask = taskService.markTaskAsCompleted(taskId, userId);
    if (updatedTask == null) {
      throw new TaskNotFoundException(ERROR_UPDATE_TASK);
    }

    TaskResponseDto responseDto = taskMapper.toResponseDto(updatedTask);
    return ResponseEntity.ok(responseDto);
  }

  @DeleteMapping(UrlConstants.DELETE_TASK)
  public ResponseEntity<String> deleteTask(
          @PathVariable Long userId,
          @PathVariable Long taskId) {

    boolean deleted = taskService.deleteTaskByUser(userId, taskId);
    if (!deleted) {
      throw new TaskNotFoundException(ERROR_DELETE_TASK);
    }

    return ResponseEntity.ok(SuccessConstants.TASK_DELETE_SUCCESS);
  }
}
