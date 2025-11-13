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
   * Finds a user by their email address.
   *
   * @param email the email of the user
   * @return an {@link Optional} containing the found {@link User}, or empty if not found
   */
  Optional<User> findUserByEmail(String email);

}
