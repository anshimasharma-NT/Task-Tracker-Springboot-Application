package com.example.tasktracker.repositories;

import com.example.tasktracker.entities.Task;
import com.example.tasktracker.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

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
   * @return list of tasks belonging to the user
   */
  List<Task> findByUserIdOrderByIdAsc(Long userId);
}
