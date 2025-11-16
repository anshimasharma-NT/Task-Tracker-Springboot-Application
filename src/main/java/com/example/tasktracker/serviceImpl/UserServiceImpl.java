package com.example.tasktracker.serviceImpl;

import com.example.tasktracker.entities.User;
import com.example.tasktracker.repositories.UserRepository;
import com.example.tasktracker.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link UserService} that handles user authentication and data persistence.
 *
 * <p>This service interacts with the {@link UserRepository} to manage user entities,
 * including retrieval, validation, and secure password storage using {@link PasswordEncoder}.</p>
 *
 * <p>It is annotated with {@link Service} to indicate that it is a Spring-managed
 * service component.</p>
 *
 * @since 1.0
 */
@Service
public class UserServiceImpl implements UserService {

  /** Repository for user data persistence. */
  @Autowired
  private UserRepository userRepository;

  /** Encoder for securing user passwords. */
  @Autowired
  private PasswordEncoder passwordEncoder;

  /**
   * Retrieves a user by their email and verifies the password.
   *
   * @param email the user's email
   * @param password the user's password
   * @return the {@link User} if credentials are valid, otherwise {@code null}
   */
  @Override
  public User getUserByEmailAndPassword(final String email, final String password) {
    User user = userRepository.findUserByEmail(email).orElse(null);
    if (user != null && passwordEncoder.matches(password, user.getPassword())) {
      return user;
    }
    return null;
  }

  /**
   * Retrieves a user by their unique ID.
   *
   * @param id the user's ID
   * @return the {@link User} if found, otherwise {@code null}
   */
  @Override
  public User getById(final Long id) {
    return userRepository.findById(Math.toIntExact(id)).orElse(null);
  }

  /**
   * Stores a new user or updates an existing user, encoding the password before saving.
   *
   * @param user the {@link User} to store
   * @return the saved {@link User} entity
   */
  @Override
  public User storeUserData(final User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userRepository.save(user);
  }
}
