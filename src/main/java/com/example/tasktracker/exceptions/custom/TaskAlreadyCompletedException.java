package com.example.tasktracker.exceptions.custom;

public class TaskAlreadyCompletedException extends RuntimeException {
  public TaskAlreadyCompletedException(String message) {
    super(message);
  }
}
