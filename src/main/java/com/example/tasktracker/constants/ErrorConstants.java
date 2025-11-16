package com.example.tasktracker.constants;

/**
 * {@summary
 * This interface contains constant values used for logging error messages
 * in UserController, TaskController, and validation classes.
 * }
 */
public interface ErrorConstants {

  /** No-op default method to prevent interface from being empty. */
  default void constantInterface() {}

  /** Error while registering user message. */
  String ERROR_REGISTER_MESSAGE = "Error while registering user";

  /** Error while logging in user message. */
  String ERROR_LOGIN_MESSAGE = "Error while logging in user: Invalid Credentials";

  /** Error when user not found. */
  String ERROR_USER_NOT_FOUND = "User not found with ID: {}";

  /** Error while creating task. */
  String ERROR_CREATE_TASK = "Error creating task.";

  /** Error while retrieving tasks. */
  String ERROR_RETRIEVE_TASKS = "Error retrieving tasks.";

  /** Error while updating task. */
  String ERROR_UPDATE_TASK = "Error updating task: Task not found or not owned by user.";

  /** Error while deleting task. */
  String ERROR_DELETE_TASK = "Error deleting task: Task not found or you are not allowed to delete.";

  /** Invalid status value for task. */
  String ERROR_INVALID_TASK_STATUS = "Invalid task status: ";
}
