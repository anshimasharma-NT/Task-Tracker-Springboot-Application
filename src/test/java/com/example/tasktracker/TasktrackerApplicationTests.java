package com.example.tasktracker;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Tests for {@link TaskTrackerApplication}.
 */
@SpringBootTest
class TaskTrackerApplicationTest {

  /**
   * Ensures the Spring application context loads successfully.
   */
  @Test
  void contextLoads() {
    // If the application context fails to load, this test will fail automatically.
  }

  /**
   * Ensures the main method executes without throwing exceptions.
   */
  @Test
  void mainMethodRunsSuccessfully() {
    TaskTrackerApplication.main(new String[]{});
  }
}
