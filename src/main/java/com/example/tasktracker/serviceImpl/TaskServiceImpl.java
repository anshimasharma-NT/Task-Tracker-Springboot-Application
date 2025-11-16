package com.example.tasktracker.serviceImpl;

import com.example.tasktracker.entities.Task;
import com.example.tasktracker.entities.TaskStatus;
import com.example.tasktracker.entities.User;
import com.example.tasktracker.repositories.TaskRepository;
import com.example.tasktracker.services.TaskService;
import com.example.tasktracker.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;

/**
 * Implementation of {@link TaskService} providing task management logic.
 */
@Service
public class TaskServiceImpl implements TaskService {

  /** Repository for task persistence. */
  @Autowired
  private TaskRepository taskRepository;

  /** Service for user. */
  @Autowired
  private UserService userService;

  /**
   * Creates a new task for a given user.
   *
   * @param task the task entity to be created
   * @param userId the owner of the task
   * @return the saved task
   */
  @Override
  public Task createTask(final Task task, final Long userId) {
    task.setUserId(userId);
    return taskRepository.save(task);
  }

  /**
   * Fetches a task by its ID and associated user ID.
   *
   * @param taskId the ID of the task
   * @param userId the ID of the user
   * @return the Task if found, otherwise null
   */
  public Task getTaskByIdAndUser(Long taskId, Long userId) {
    return (Task) taskRepository.findByIdAndUserId(taskId, userId).orElse(null);
  }

  /**
   * Marks a task as completed.
   *
   * <p>Only the owner of the task is allowed to mark it as completed.
   * Throws an {@link IllegalArgumentException} if the task does not exist
   * or the user is not the owner.</p>
   *
   * @param taskId the ID of the task to be updated
   * @param userId the ID of the user attempting the update
   * @return the updated task
   * @throws IllegalArgumentException if the task is not found or the user is not the owner
   */
  @Override
  public Task markTaskAsCompleted(final Long taskId, final Long userId) {
    Task task = taskRepository.findById(taskId)
            .orElseThrow(() -> new IllegalArgumentException("Task not found with id: " + taskId));

    if (!task.getUserId().equals(userId)) {
      throw new IllegalArgumentException("You are not allowed to update this task");
    }

    task.setStatus(TaskStatus.COMPLETED);
    return taskRepository.save(task);
  }

  /**
   * Deletes a task belonging to a specific user.
   *
   * <p>Only deletes the task if it exists and is owned by the user.</p>
   *
   * @param userId the ID of the user attempting the deletion
   * @param taskId the ID of the task to delete
   * @return {@code true} if the task was deleted, {@code false} otherwise
   */
  @Override
  public boolean deleteTaskByUser(final Long userId, final Long taskId) {
    User user = userService.getById(userId);
    if (user == null) {
      return false;
    }

    return taskRepository.findById(taskId)
            .filter(task -> task.getUserId().equals(userId))
            .map(task -> {
              taskRepository.delete(task);
              return true;
            })
            .orElse(false);
  }

  /**
   * Retrieves all tasks associated with a specific user, optionally filtered by status and due date,
   * and paginated.
   *
   * @param userId the user whose tasks are to be retrieved
   * @param status optional filter by {@link TaskStatus}, can be null
   * @param dueDate optional filter by due date, can be null
   * @param page the page number (0-based)
   * @param size the number of tasks per page
   * @return a list of tasks for the given user matching the filters
   */
  @Override
  public List<Task> getTasksByUserWithFilters(
          final Long userId,
          final TaskStatus status,
          final LocalDate dueDate,
          final int page,
          final int size) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id")); // sorted by id ascending
    return taskRepository.findTasksByFilters(userId, status, dueDate, pageable).getContent();
  }
}
