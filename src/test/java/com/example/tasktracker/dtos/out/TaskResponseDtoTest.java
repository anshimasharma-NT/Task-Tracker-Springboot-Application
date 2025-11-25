package com.example.tasktracker.dtos.out;

import com.example.tasktracker.entities.TaskStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link TaskResponseDto}.
 */
class TaskResponseDtoTest {

  @Test
  void testGetterAndSetter() {
    TaskResponseDto dto = new TaskResponseDto(
            1L,
            "Task 1",
            "Description 1",
            TaskStatus.PENDING,
            LocalDate.of(2025, 11, 25),
            LocalDateTime.of(2025, 11, 20, 10, 30)
    );

    assertEquals(1L, dto.getTaskId());
    assertEquals("Task 1", dto.getTitle());
    assertEquals("Description 1", dto.getDescription());
    assertEquals(TaskStatus.PENDING, dto.getStatus());
    assertEquals(LocalDate.of(2025, 11, 25), dto.getDueDate());
    assertEquals(LocalDateTime.of(2025, 11, 20, 10, 30), dto.getCreatedAt());

    // Update values
    dto.setTaskId(2L);
    dto.setTitle("Task 2");
    dto.setDescription("Description 2");
    dto.setStatus(TaskStatus.COMPLETED);
    dto.setDueDate(LocalDate.of(2025, 12, 1));
    dto.setCreatedAt(LocalDateTime.of(2025, 12, 1, 12, 0));

    assertEquals(2L, dto.getTaskId());
    assertEquals("Task 2", dto.getTitle());
    assertEquals("Description 2", dto.getDescription());
    assertEquals(TaskStatus.COMPLETED, dto.getStatus());
    assertEquals(LocalDate.of(2025, 12, 1), dto.getDueDate());
    assertEquals(LocalDateTime.of(2025, 12, 1, 12, 0), dto.getCreatedAt());
  }

  @Test
  void testToString() {
    TaskResponseDto dto = new TaskResponseDto(
            1L,
            "Task 1",
            "Description 1",
            TaskStatus.PENDING,
            LocalDate.of(2025, 11, 25),
            LocalDateTime.of(2025, 11, 20, 10, 30)
    );

    String expected = "TaskResponseDto(taskId=1, title=Task 1, description=Description 1, " +
            "status=PENDING, dueDate=2025-11-25, createdAt=2025-11-20T10:30)";
    assertEquals(expected, dto.toString());
  }

  @Test
  void testEqualsAndHashCode() {
    TaskResponseDto dto1 = new TaskResponseDto(
            1L, "Task 1", "Description", TaskStatus.PENDING,
            LocalDate.of(2025, 11, 25), LocalDateTime.of(2025, 11, 20, 10, 30)
    );

    TaskResponseDto dto2 = new TaskResponseDto(
            1L, "Task 1", "Description", TaskStatus.PENDING,
            LocalDate.of(2025, 11, 25), LocalDateTime.of(2025, 11, 20, 10, 30)
    );

    TaskResponseDto dto3 = new TaskResponseDto(
            2L, "Task 2", "Desc", TaskStatus.COMPLETED,
            LocalDate.of(2025, 12, 1), LocalDateTime.of(2025, 12, 1, 12, 0)
    );

    assertEquals(dto1, dto2);
    assertEquals(dto1.hashCode(), dto2.hashCode());

    assertNotEquals(dto1, dto3);
    assertNotEquals(dto1.hashCode(), dto3.hashCode());
  }
}
