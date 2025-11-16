package com.example.tasktracker.constants;

public interface UrlConstants {

  /**
   * No-op default method to prevent interface from being empty.
   */
  default void constantInterface() {
  }

  /**
   * Endpoint path for user-related operations.
   */
  String USER = "/user";

  /**
   * Endpoint path for registration.
   */
  String REGISTER = "/register";

  /**
   * Endpoint path for login operations.
   */
  String LOGIN = "/login";

  /**
   * Endpoint path for task-related operations.
   */
  String TASK = "/task";

  /**
   * Endpoint path for adding new task.
   */
  String ADD_TASK = "/{userId}/addTask";

  /**
   * Endpoint path for getting tasks by user.
   */
  String GET_TASKS_LIST = "/{userId}/getTasksByUser";

  /**
   * Endpoint path for marking status as completed.
   */
  String COMPLETE_TASK = "/complete/{taskId}/{userId}";

  /**
   * Endpoint path for deletion of user-specific task.
   */
  String DELETE_TASK = "/{userId}/deleteTask/{taskId}";
}

