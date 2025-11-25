package com.example.tasktracker.repositories;

import com.example.tasktracker.entities.Task;
import com.example.tasktracker.entities.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Repository interface for managing {@link Task} entities.
 *
 * <p>This interface provides methods for retrieving, storing, and managing
 * tasks associated with specific users. It extends {@link JpaRepository}
 * to leverage Spring Data JPA functionality for CRUD operations.</p>
 *
 * <p>Typical usage example:</p>
 * <pre>{@code
 * Page<Task> tasks = taskRepository.findAllByUser(user, pageable);
 * }</pre>
 *
 * @author Your Name
 * @since 1.0
 */
public interface TaskRepository extends JpaRepository<Task, Long> {

  /**
   * Returns true if a task with the given title already exists for the specified user.
   * The title check is case-insensitive.
   *
   * @param userId the ID of the user
   * @param title the title of the task
   * @return true if a duplicate task exists for the user, false otherwise
   */
  boolean existsByUserIdAndTitleIgnoreCase(Long userId, String title);

  /**
   * Retrieves a page of tasks for a specific user.
   *
   * @param userId   ID of the user
   * @param pageable Pagination information
   * @return a page of tasks
   */
  Page<Task> findByUserId(Long userId, Pageable pageable);

  /**
   * Retrieves a page of tasks for a specific user filtered by status.
   *
   * @param userId   ID of the user
   * @param status   Status of the tasks
   * @param pageable Pagination information
   * @return a page of tasks
   */
  Page<Task> findByUserIdAndStatus(Long userId, TaskStatus status, Pageable pageable);

  /**
   * Retrieves a page of tasks for a specific user filtered by due date.
   *
   * @param userId   ID of the user
   * @param dueDate  Due date of the tasks
   * @param pageable Pagination information
   * @return a page of tasks
   */
  Page<Task> findByUserIdAndDueDate(Long userId, LocalDate dueDate, Pageable pageable);

  /**
   * Retrieves a page of tasks for a specific user filtered by status and due date.
   *
   * @param userId   ID of the user
   * @param status   Status of the tasks
   * @param dueDate  Due date of the tasks
   * @param pageable Pagination information
   * @return a page of tasks
   */
  Page<Task> findByUserIdAndStatusAndDueDate(Long userId, TaskStatus status, LocalDate dueDate, Pageable pageable);

  /**
   * Retrieves tasks for a specific user.
   *
   * @param userId   ID of the user
   * @param taskId   ID of the task
   * @return tasks
   */
  Optional<Task> findByTaskIdAndUserId(Long taskId, Long userId);

}
