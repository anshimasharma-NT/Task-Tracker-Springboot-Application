package com.example.tasktracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Task Tracker Spring Boot application.
 */
@SpringBootApplication
public final class TaskTrackerApplication {

  private TaskTrackerApplication() {

    //private constructor
  }


  /**
   * Main method that starts the Spring Boot application.
   *
   * @param args command-line arguments
   */

  public static void main(final String[] args) {
    SpringApplication.run(TaskTrackerApplication.class, args);
  }
}
