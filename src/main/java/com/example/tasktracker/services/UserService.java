package com.example.tasktracker.services;

import com.example.tasktracker.entities.User;

/**
 * Service interface for managing user-related operations in the Task Tracker application.
 *
 * <p>This interface defines methods for retrieving, validating, and storing
 * user data, such as authentication and profile management.</p>
 *
 * <p>Implementations of this interface should provide the business logic
 * for interacting with the underlying user data source.</p>
 *
 * @author Your Name
 * @since 1.0
 */
public interface UserService {

  /**
   * Retrieves a user by their email and password.
   *
   * @param email the email of the user
   * @param password the password of the user
   * @return the {@link User} if found, otherwise {@code null}
   */
  User getUserByEmailAndPassword(String email, String password);

  /**
   * Retrieves a user by their unique ID.
   *
   * @param id the ID of the user
   * @return the {@link User} with the given ID, otherwise {@code null}
   */
  User getById(Long id);

  /**
   * Saves or updates the given user data in the data source.
   *
   * @param user the user to store
   * @return the persisted {@link User} entity
   */
  User storeUserData(User user);
}
