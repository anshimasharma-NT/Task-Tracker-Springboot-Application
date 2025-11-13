package com.example.tasktracker.serviceImpl;

import com.example.tasktracker.entities.Task;
import com.example.tasktracker.entities.TaskStatus;
import com.example.tasktracker.entities.User;
import com.example.tasktracker.repositories.TaskRepository;
import com.example.tasktracker.services.TaskService;
import com.example.tasktracker.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Implementation of {@link TaskService} providing task management logic.
 */
@Service
public class TaskServiceImpl implements TaskService {

  /** Repository for task persistence. */
  @Autowired
  private TaskRepository taskRepository;

  /** Service for user. **/
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
   * Retrieves all tasks associated with a specific user, ordered by task ID.
   *
   * @param userId the user whose tasks are to be retrieved
   * @return a list of tasks for the given user
   */
  @Override
  public List<Task> getTasksByUser(final Long userId) {
    return taskRepository.findByUserIdOrderByIdAsc(userId);
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

}
