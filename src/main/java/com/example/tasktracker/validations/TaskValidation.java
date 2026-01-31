package com.example.tasktracker.validations;

import com.example.tasktracker.constants.UserConstants;
import com.example.tasktracker.dtos.in.TaskRequestDto;
import com.example.tasktracker.entities.Task;
import com.example.tasktracker.exceptions.custom.AlreadyExistsException;
import com.example.tasktracker.exceptions.custom.NotFoundException;
import com.example.tasktracker.exceptions.custom.ValidationException;
import com.example.tasktracker.repositories.TaskRepository;
import com.example.tasktracker.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



/**
 * Validation utility for Task entity operations and filter parameters.
 * Ensures that task data meets required constraints before persisting
 * and validates filters used for task retrieval.
 */
@Component
public class TaskValidation {

  /**
   * Logger instance for logging events and errors.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(TaskValidation.class);
  /**
   * Repository for performing CRUD operations.
   */
  @Autowired
  private UserRepository userRepository;
  /**
   * Repository for performing CRUD operations on {@link Task}.
   */
  @Autowired
  private TaskRepository taskRepository;
  /**
   * Validate the {@link TaskRequestDto} object.
   * @param taskRequestDto containing task details
   * @throws ValidationException if any validation rule fails.
   */
  public void validateAddTask(final TaskRequestDto taskRequestDto) {

    if (taskRepository.existsByUserIdAndTitleIgnoreCase(taskRequestDto.getUserId(), taskRequestDto.getTitle().trim())) {
      throw new AlreadyExistsException("Task with the same title already exists for this user.");
    }
  }
  /**
   * Validates if the task with the given ID belongs to the specified user.
   *
   * @param taskId the ID of the task to validate
   * @throws ValidationException if the task does not exist or does not belong to the user
   * @return Task object
   */
  public Task validateTaskID(final Long taskId) {
    return taskRepository.findById(taskId)
            .orElseThrow(() -> new ValidationException("Task with ID " + taskId + " does not exist."));

  }
  /**
   * Validates if the task with the given ID belongs to the specified user.
   *
   * @param userId the ID of who created the task.
   * @throws ValidationException if the task does not exist or does not belong to the user
   */
  public void validateUserID(final Long userId) {
    if (!userRepository.existsByUserId(userId)) {
      throw new NotFoundException(UserConstants.USER_NOT_FOUND_MESSAGE);
    }
  }
}
