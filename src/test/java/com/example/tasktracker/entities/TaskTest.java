package com.example.tasktracker.entities;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link Task}.
 */
class TaskTest {

  /**
   * Tests getters, setters, and default values.
   */
  @Test
  void testGetterAndSetter() {
    Task task = new Task();

    assertNull(task.getTaskId());
    Long id = 101L;
    task.setTaskId(id);
    assertEquals(id, task.getTaskId());

    assertNull(task.getTitle());
    String title = "Task title";
    task.setTitle(title);
    assertEquals(title, task.getTitle());

    assertNull(task.getDescription());
    String desc = "Task Description";
    task.setDescription(desc);
    assertEquals(desc, task.getDescription());

    assertEquals(TaskStatus.PENDING, task.getStatus());
    task.setStatus(TaskStatus.COMPLETED);
    assertEquals(TaskStatus.COMPLETED, task.getStatus());

    assertNull(task.getUserId());
    Long userId = 55L;
    task.setUserId(userId);
    assertEquals(userId, task.getUserId());

    assertNull(task.getDueDate());
    LocalDate date = LocalDate.of(2025, 5, 20);
    task.setDueDate(date);
    assertEquals(date, task.getDueDate());
  }

  /**
   * Tests @PrePersist method automatically setting createdAt.
   */
  @Test
  void testPrePersistSetsCreatedAt() {
    Task task = new Task();

    assertNull(task.getCreatedAt());

    task.prePersist();

    assertNotNull(task.getCreatedAt());
    assertTrue(task.getCreatedAt().isBefore(LocalDateTime.now().plusSeconds(1)));
    assertTrue(task.getCreatedAt().isAfter(LocalDateTime.now().minusSeconds(5)));
  }

  /**
   * Tests the toString() method.
   */
  @Test
  void testToString() {
    LocalDate due = LocalDate.of(2025, 3, 15);
    LocalDateTime createdAt = LocalDateTime.of(2025, 1, 20, 10, 30);

    Task task = new Task(
            10L,
            "Build Feature",
            "Develop task module",
            TaskStatus.PENDING,
            2L,
            due,
            createdAt
    );

    String expected = "Task(taskId=10, title=Build Feature, description=Develop task module, " +
            "status=PENDING, userId=2, dueDate=2025-03-15, createdAt=2025-01-20T10:30)";

    assertEquals(expected, task.toString());
  }

  /**
   * Tests equals() and hashCode().
   */
  @Test
  void testEqualsAndHashCode() {
    LocalDate date = LocalDate.of(2025, 6, 10);

    Task task1 = new Task(1L, "Task A", "Desc", TaskStatus.PENDING, 5L, date, null);
    Task task2 = new Task(1L, "Task A", "Desc", TaskStatus.PENDING, 5L, date, null);
    Task task3 = new Task(2L, "Task B", "Another", TaskStatus.COMPLETED, 5L, date, null);

    assertEquals(task1, task2);
    assertEquals(task1.hashCode(), task2.hashCode());

    assertNotEquals(task1, task3);
    assertNotEquals(task1.hashCode(), task3.hashCode());
  }
}
