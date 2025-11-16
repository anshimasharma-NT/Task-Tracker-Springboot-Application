package com.example.tasktracker.entities;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class TaskTest {

  @Test
  public void testGetterAndSetter() {
    Task task = new Task();

    // Test id
    assertNull(task.getId());
    Long id = 1L;
    task.setId(id);
    assertEquals(id, task.getId());

    // Test title
    assertNull(task.getTitle());
    String title = "Complete Assignment";
    task.setTitle(title);
    assertEquals(title, task.getTitle());

    // Test description
    assertNull(task.getDescription());
    String description = "Finish the JPA assignment";
    task.setDescription(description);
    assertEquals(description, task.getDescription());

    // Test dueDate
    assertNull(task.getDueDate());
    LocalDate dueDate = LocalDate.of(2025, 11, 30);
    task.setDueDate(dueDate);
    assertEquals(dueDate, task.getDueDate());

    // Test status
    assertEquals(TaskStatus.PENDING, task.getStatus()); // default value
    task.setStatus(TaskStatus.COMPLETED);
    assertEquals(TaskStatus.COMPLETED, task.getStatus());

    // Test userId
    assertNull(task.getUserId());
    Long userId = 10L;
    task.setUserId(userId);
    assertEquals(userId, task.getUserId());

    // Test createdAt
    LocalDateTime createdAt = LocalDateTime.now();
    task.setCreatedAt(createdAt);
    assertEquals(createdAt, task.getCreatedAt());

  }

  @Test
  public void testBuilder() {
    LocalDateTime createdAt = LocalDateTime.of(2025, 11, 16, 12, 0);
    LocalDate dueDate = LocalDate.of(2025, 11, 30);

    Task task = Task.builder()
            .id(1L)
            .title("Complete Assignment")
            .description("Finish the JPA assignment")
            .dueDate(dueDate)
            .status(TaskStatus.PENDING)
            .userId(10L)
            .createdAt(createdAt)
            .build();

    assertEquals(1L, task.getId());
    assertEquals("Complete Assignment", task.getTitle());
    assertEquals("Finish the JPA assignment", task.getDescription());
    assertEquals(dueDate, task.getDueDate());
    assertEquals(TaskStatus.PENDING, task.getStatus());
    assertEquals(10L, task.getUserId());
    assertEquals(createdAt, task.getCreatedAt());
  }

  @Test
  public void testEnsureDefaultStatus() {
    Task task = new Task();
    task.setTitle("Test Task");
    task.setUserId(1L);
    task.setCreatedAt(null);
    task.setStatus(null);

    task.ensureDefaultStatus();

    assertEquals(TaskStatus.PENDING, task.getStatus());
    assertNotNull(task.getCreatedAt());
  }
}
