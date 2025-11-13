package com.example.tasktracker.services;

import com.example.tasktracker.entities.Task;
import com.example.tasktracker.entities.User;
import java.util.List;

/**
 * Service interface for managing tasks in the Task Tracker application.
 *
 * <p>This interface defines methods for creating, retrieving, and managing tasks.</p>
 */
public interface TaskService {

  /**
   * Creates and saves a new task.
   *
   * @param task the task to create
   * @param userId the user id who owns the task
   * @return the saved {@link Task}
   */
  Task createTask(Task task, Long userId);

  /**
   * Retrieves all tasks belonging to a specific user.
   *
   * @param userId the user whose tasks to retrieve
   * @return list of tasks belonging to the user
   */
  List<Task> getTasksByUser(Long userId);

  /**
   * Retrieves a task by its unique ID and then mark its taskStatus as completed.
   *
   * @param taskId the ID of the task
   * @param userId the ID of the user
   * @return the {@link Task} if found, otherwise {@code null}
   */
  Task markTaskAsCompleted(Long taskId, Long userId);

  /**
   * Retrieves a task by its unique ID and then delete that task.
   *
   * @param userId the ID of the user
   * @param taskId the ID of the task
   * @return the {@link Task} if found, otherwise {@code null}
   */
  boolean deleteTaskByUser(Long userId, Long taskId);
}
