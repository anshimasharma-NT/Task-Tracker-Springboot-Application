package com.example.tasktracker.constants;

/**
 * Interface that defines URL path constants.
 */
public interface UrlConstants {
  /**
   * No-op default method to prevent interface from being empty.
   */
  default void constantInterface() {
  }
  /**
   * Auth Endpoint.
   */
  String USER_ENDPOINT = "/api/user";
  /**
   * User-related operations.
   */
  String LOGIN = "/login";
  /**
   * User-related operations.
   */
  String REGISTER = "/register";
  /**
   * Task management endpoint.
   */
  String TASK_ENDPOINT = "/api/task";
  /**
   * Adding task.
   */
  String ADD_TASK = "/addTask";
  /**
   * Mark task as complete.
   */
  String MARK_TASK_AS_COMPLETED = "/{taskId}/user/{userId}/completeTask";
  /**
   * Delete task.
   */
  String DELETE_TASK = "/{taskId}/user/{userId}/deleteTask";
  /**
   * Get User Task.
   */
  String GET_TASKS_BY_USER = "/user/{userId}/getTasksByUser";
}
