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
   * Finds all tasks belonging to a specific user.
   *
   * @param userId the user whose tasks should be fetched
   * @param status the user's task status
   * @param dueDate the task's dueDate
   * @param pageable for pagination
   * @return list of tasks belonging to the user
   */

  @Query("SELECT t FROM Task t "
          + "WHERE t.userId = :userId "
          + "AND (:status IS NULL OR t.status = :status) "
          + "AND (:dueDate IS NULL OR t.dueDate = :dueDate)")
  Page<Task> findTasksByFilters(@Param("userId") Long userId,
                                @Param("status") TaskStatus status,
                                @Param("dueDate") LocalDate dueDate,
                                Pageable pageable);


  /**
   * Finds all tasks belonging to a specific user.
   *
   * @param userId the id of the user
   * @param taskId the id of the task
   * @return specific task of specific user
   */
  Optional<Object> findByIdAndUserId(Long taskId, Long userId);

  /**
   * Find task if already added by specific user.
   *
   * @param userId the id of the user
   * @param title the title of the task
   * @return true or false
   */
  boolean existsByTitleAndUserId(String title, Long userId);
}
