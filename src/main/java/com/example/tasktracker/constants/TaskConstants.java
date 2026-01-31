package com.example.tasktracker.constants;

/**
 * Contains constant values used across task-related components.
 */
public interface TaskConstants {
  /**
   * No-op default method to prevent interface from being empty.
   */
  default void constantInterface() {
  }
  /**
   * Log message when a task request is received.
   */
  String ADD_TASK_MESSAGE =
          "Received request to add task with title: {}";
  /**
   * Message returned when user registration is successful.
   */
  String TASK_ADDED_SUCCESS_MESSAGE =
          "Task added successfully.";
  /**
   * Log message when user request for marking task as complete.
   */
  String TASK_MARK_COMPLETE_REQUEST_MESSAGE =
          "Received request to mark task as complete with taskID: {} for userID: {}";
  /**
   *  Log message when task is marked as complete.
   */

  String TASK_MARK_COMPLETE_SUCCESS_MESSAGE =
          "Task with taskID {} for userID: {} marked as completed successfully.";
  /**
   *  Log message when a task is already completed.
   */
  String TASK_ALREADY_COMPLETE_MESSAGE =
          "Task is already marked as completed.";
  /**
   *  Log message when deleting a task .
   */
  String TASK_DELETE_MESSAGE =
          "Received request to delete task with taskID: {} for userID: {}.";
  /**
   * Log message when task is deleted successfully .
   */
  String TASK_DELETE_SUCCESS_MESSAGE =
          "Task with taskID {} deleted successfully.";
  /**
   * Log message used when a request is made to fetch a user's task list.
   */
  String TASK_FETCH_REQUEST_MESSAGE =
          "Fetching task list for userId={}, page={}, size={}, dueDate={}, status={}";
  /**
   * Log message used when a user's task list has been successfully fetched.
   */
  String TASK_FETCH_SUCCESS_MESSAGE =
          "Successfully fetched {} tasks for userId={}";
  /**
   * Message shown when the provided due date is not later than today's date.
   */
  String INVALID_DUE_DATE_MESSAGE =
          "Due date must be greater than the current date";


}
