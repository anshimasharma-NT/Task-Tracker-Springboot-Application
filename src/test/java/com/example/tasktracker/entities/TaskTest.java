package com.example.tasktracker.entities;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.cglib.core.Local;

public class TaskTest {

  @Test
  public void testGetterAndSetter() {
    Task task = new Task();

    assertNull(task.getId());
    Long id = 1L;
    task.setId(id);
    assertEquals(id, task.getId());

    assertNull(task.getTitle());
    String title = "Complete Assignment";
    task.setTitle(title);
    assertEquals(title, task.getTitle());

    assertNull(task.getDescription());
    String description = "Finish the JPA assignment";
    task.setDescription(description);
    assertEquals(description, task.getDescription());

    assertNull(task.getDueDate());
    LocalDate dueDate = LocalDate.of(2025, 11, 30);
    task.setDueDate(dueDate);
    assertEquals(dueDate, task.getDueDate());

    assertEquals(TaskStatus.PENDING, task.getStatus()); // default value
    task.setStatus(TaskStatus.COMPLETED);
    assertEquals(TaskStatus.COMPLETED, task.getStatus());

    assertNull(task.getUserId());
    Long userId = 10L;
    task.setUserId(userId);
    assertEquals(userId, task.getUserId());

    LocalDateTime createdAt = LocalDateTime.now();
    task.setCreatedAt(createdAt);
    assertEquals(createdAt, task.getCreatedAt());

  }

  @Test
  public void testToString() {
    Task task = new Task();
    Long id = 1L;
    String title = "Spring boot task";
    String description = "Need to complete spring boot application by tomorrow.";
    LocalDate dueDate = LocalDate.of(2025, 11, 30);
    LocalDateTime createdAt = LocalDateTime.now();
    Long userId = 2L;
    TaskStatus status = TaskStatus.COMPLETED;


    task.setId(id);
    task.setTitle(title);
    task.setDescription(description);
    task.setDueDate(dueDate);
    task.setStatus(status);
    task.setCreatedAt(createdAt);
    task.setUserId(userId);

    String expected = "Task [id=" + id + ", title=" +title+ ", description=" +description+
            ",dueDate="+dueDate+ ", status=" + status + ", userId=" +userId+ ", createdAt=" +createdAt+ "]";
    assertEquals(expected, task.toString());

  }

  @Test
  public void testEqualsAndHashCode() {
    Long id = 1L;
    String title = "Spring boot task";
    String description = "Need to complete spring boot application by tomorrow.";
    LocalDate dueDate = LocalDate.of(2025,11,30);
    TaskStatus status = TaskStatus.COMPLETED;
    Long userId = 3L;
    LocalDateTime createdAt = LocalDateTime.now();

    Task task1 = setUpTask(id, title, description, dueDate, status, userId, createdAt);

    Task task2 = setUpTask(id, title, description, dueDate, status, userId, createdAt);
    assertEquals(task1, task2);
    assertEquals(task1.hashCode(), task2.hashCode());

    task2 = setUpTask(2L, title, description, dueDate, status, userId, createdAt);
    assertNotEquals(task1, task2);
    assertNotEquals(task1.hashCode(), task2.hashCode());

    task2 = setUpTask(id, "New title", description, dueDate, status, userId, createdAt);
    assertNotEquals(task1, task2);
    assertNotEquals(task1.hashCode(), task2.hashCode());

    task2 = setUpTask(id, title, description, dueDate, TaskStatus.PENDING, userId, createdAt);
    assertNotEquals(task1, task2);
    assertNotEquals(task1.hashCode(), task2.hashCode());

    task2 = setUpTask(id, title, description, dueDate, status, 4L, createdAt);
    assertNotEquals(task1, task2);
    assertNotEquals(task1.hashCode(), task2.hashCode());

    task2 = setUpTask(id, title, description, dueDate, status, userId, createdAt.plusDays(1));
    assertNotEquals(task1, task2);
    assertNotEquals(task1.hashCode(), task2.hashCode());
  }

  private Task setUpTask(Long id, String title, String description, LocalDate dueDate, TaskStatus status, Long userId, LocalDateTime createdAt) {
    Task task = new Task();
    task.setId(id);
    task.setTitle(title);
    task.setDescription(description);
    task.setDueDate(dueDate);
    task.setStatus(status);
    task.setUserId(userId);
    task.setCreatedAt(createdAt);
    return task;
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

  private Task setUpTask(Long id, String title, String description, LocalDate dueDate, TaskStatus status, LocalDateTime createdAt, Long userId) {
    Task task = new Task();
    task.setId(id);
    task.setTitle(title);
    task.setDescription(description);
    task.setDueDate(dueDate);
    task.setStatus(status);
    task.setCreatedAt(createdAt);
    task.setUserId(userId);
    return task;

  }
}
