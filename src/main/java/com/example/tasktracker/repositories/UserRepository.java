package com.example.tasktracker.repositories;

import com.example.tasktracker.entities.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link User} entities.
 *
 * <p>This interface provides CRUD operations and custom query methods
 * for accessing user data from the underlying data source.</p>
 *
 * <p>It extends {@link JpaRepository} to leverage Spring Data JPA functionality.</p>
 *
 * @author Your Name
 * @since 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  /**
   * @param email the email address of user.
   * @return {@link User} if found.
   */
  User findByEmail(String email);
  /**
   * @param email the email address to check for existence
   * @return {@code true} if a user exists with the specified email, otherwise {@code false}
   */
  boolean existsByEmail(String email);

  /**
   * Checks whether a task (or entity) exists for the given user ID.
   * @param userId the ID of the user to check for
   * @return true if an entity with the specified user ID exists, false otherwise
   */
  boolean existsByUserId(Long userId);

}
