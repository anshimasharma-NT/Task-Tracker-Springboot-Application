package com.example.tasktracker.constants;

/**
 * {@summary
 * This class contains constant values used for logging messages in the
 * UserController and TaskController.
 * }
 */
public interface SuccessConstants {

  /**
   * No-op default method to prevent interface from being empty.
   */
  default void constantInterface() {
  }

  /** Success message while user register. */
  String USER_REGISTER_SUCCESS = "User successfully registered: {}";

  /** Success message while user login. */
  String USER_LOGIN_SUCCESS = "User logged in successfully: {}";

  /** Success message when a task is created. */
  String TASK_CREATE_SUCCESS = "Task '{}' created for user {}";

  /** Success message when a task is deleted. */
  String TASK_DELETE_SUCCESS = "Task deleted successfully: {}";
}
