package com.example.tasktracker.serviceImpl;

import com.example.tasktracker.constants.NumericConstants;
import com.example.tasktracker.constants.TaskConstants;
import com.example.tasktracker.dtos.in.TaskRequestDto;
import com.example.tasktracker.dtos.out.PaginatedTaskResponseDto;
import com.example.tasktracker.dtos.out.ApiResponseDto;
import com.example.tasktracker.dtos.out.TaskResponseDto;
import com.example.tasktracker.entities.Task;
import com.example.tasktracker.entities.TaskStatus;
import com.example.tasktracker.entities.User;
import com.example.tasktracker.exceptions.custom.AlreadyExistsException;
import com.example.tasktracker.exceptions.custom.ValidationException;
import com.example.tasktracker.repositories.TaskRepository;
import com.example.tasktracker.repositories.UserRepository;
import com.example.tasktracker.services.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Implementation of {@link TaskService} that manages task operations .
 */
@Service
public class TaskServiceImpl implements TaskService {

  /**
   * Logger instance for logging events and errors.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(TaskServiceImpl.class);

  /**
   * Repository for performing CRUD operations on {@link User}.
   */
  @Autowired
  private UserRepository userRepository;

  /**
   * Repository for performing CRUD operations on {@link Task}.
   */
  @Autowired
  private TaskRepository taskRepository;

  /**
   * Creates a new task .
   * @param taskRequestDto the task creation request containing task details
   * @return a {@link ApiResponseDto} indicating successful creation
   * @throws ValidationException if date is not valid
   */
  @Override
  public ApiResponseDto addTask(final TaskRequestDto taskRequestDto) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(NumericConstants.DATE_PATTERN)
            .withResolverStyle(ResolverStyle.STRICT);
    LocalDate today = LocalDate.now();
    LocalDate dueDate = LocalDate.parse(taskRequestDto.getDueDate().trim(), formatter);
    if (!dueDate.isAfter(today)) {
      throw new ValidationException(TaskConstants.INVALID_DUE_DATE_MESSAGE);
    }
    Task task = new Task();
    task.setTitle(taskRequestDto.getTitle());
    task.setDescription(taskRequestDto.getDescription());
    task.setUserId(taskRequestDto.getUserId());
    task.setDueDate(LocalDate.parse(taskRequestDto.getDueDate().trim(), formatter));
    taskRepository.save(task);
    return new ApiResponseDto(true, TaskConstants.TASK_ADDED_SUCCESS_MESSAGE);
  }
  /**
   * Marks a task as completed.
   *
   * @param task of the task to be marked complete.
   * @return SuccessResponseDTO indicating success status and message.
   */
  @Override
  public ApiResponseDto markTaskAsComplete(final Task task) {
    if (task.getStatus() == TaskStatus.COMPLETED){
      throw new AlreadyExistsException((TaskConstants.TASK_ALREADY_COMPLETE_MESSAGE));
    }
    task.setStatus(TaskStatus.COMPLETED);
    taskRepository.save(task);
    return new ApiResponseDto(true, "Task marked as completed.");
  }
  /**
   * Deletes a task based on the provided task ID.
   *
   * @param taskId ID of the task to be deleted.
   * @return SuccessResponseDTO indicating success status and message.
   */
  @Override
  public ApiResponseDto deleteTask(final Long taskId) {
    taskRepository.deleteById(taskId);
    return new ApiResponseDto(true, "Task deleted successfully.");
  }
  /**
   * Retrieves paginated tasks for a given user, optionally filtered by due date and status.
   *
   * @param userId   ID of the user
   * @param pageable Pagination information
   * @param dueDate  Optional due date filter (yyyy-MM-dd)
   * @param status   Optional status filter (PENDING or COMPLETE)
   * @return PaginatedTaskResponseDTO containing tasks and pagination details
   */
  @Override
  public PaginatedTaskResponseDto getTasksByUser(final Long userId, final Pageable pageable,
                                               final String dueDate, final String status) {
    Page<Task> tasksPage;
    LocalDate parsedDueDate = null;
    TaskStatus parsedStatus = null;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(NumericConstants.DATE_PATTERN)
            .withResolverStyle(ResolverStyle.STRICT);
    if (dueDate != null && !dueDate.isEmpty()) {
      parsedDueDate = LocalDate.parse(dueDate, formatter);
    }
    if (status != null && !status.isEmpty()) {
      parsedStatus = TaskStatus.valueOf(status.toUpperCase(Locale.ROOT));
    }

    if (parsedDueDate != null && parsedStatus != null) {
      tasksPage = taskRepository.findByUserIdAndStatusAndDueDate(userId, parsedStatus, parsedDueDate, pageable);
    } else if (parsedDueDate != null) {
      tasksPage = taskRepository.findByUserIdAndDueDate(userId, parsedDueDate, pageable);
    } else if (parsedStatus != null) {
      tasksPage = taskRepository.findByUserIdAndStatus(userId, parsedStatus, pageable);
    } else {
      tasksPage = taskRepository.findByUserId(userId, pageable);
    }

    return new PaginatedTaskResponseDto(
            tasksPage.getContent().stream()
                    .map(task -> new TaskResponseDto(
                            task.getTaskId(),
                            task.getTitle(),
                            task.getDescription(),
                            task.getStatus(),
                            task.getDueDate(),
                            task.getCreatedAt()))
                    .collect(Collectors.toList()),
            tasksPage.getNumber(),
            tasksPage.getSize(),
            tasksPage.getTotalElements(),
            tasksPage.getTotalPages()
    );
  }

}
