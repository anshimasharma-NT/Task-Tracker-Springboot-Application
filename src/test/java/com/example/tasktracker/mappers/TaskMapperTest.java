package com.example.tasktracker.mappers;

import com.example.tasktracker.dtos.in.TaskRequestDto;
import com.example.tasktracker.dtos.out.TaskResponseDto;
import com.example.tasktracker.entities.Task;
import com.example.tasktracker.entities.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TaskMapperTest {

  private TaskMapper taskMapper;

  @BeforeEach
  void setUp() {
    taskMapper = new TaskMapper();
  }

  // --------------------------------------------------------------------
  // toEntity() Tests
  // --------------------------------------------------------------------

  @Test
  void toEntity_ShouldReturnNull_WhenDtoIsNull() {
    Task result = taskMapper.toEntity(null);
    assertNull(result);
  }

  @Test
  void toEntity_ShouldMapFieldsCorrectly() {
    LocalDate dueDate = LocalDate.now().plusDays(5);

    TaskRequestDto dto = new TaskRequestDto(
            "Test Task",
            "This is a test description",
            dueDate
    );

    Task task = taskMapper.toEntity(dto);

    assertNotNull(task);
    assertEquals("Test Task", task.getTitle());
    assertEquals("This is a test description", task.getDescription());
    assertEquals(dueDate, task.getDueDate());
  }

  @Test
  void toEntity_ShouldSetDefaultStatusToPending() {
    TaskRequestDto dto = new TaskRequestDto(
            "Another Task",
            "Description",
            LocalDate.now().plusDays(2)
    );

    Task task = taskMapper.toEntity(dto);

    assertNotNull(task);
    assertEquals(TaskStatus.PENDING, task.getStatus());
  }

  // --------------------------------------------------------------------
  // toResponseDto() Tests
  // --------------------------------------------------------------------

  @Test
  void toResponseDto_ShouldReturnNull_WhenEntityIsNull() {
    TaskResponseDto result = taskMapper.toResponseDto(null);
    assertNull(result);
  }

  @Test
  void toResponseDto_ShouldMapFieldsCorrectly() {
    LocalDate dueDate = LocalDate.now().plusDays(3);
    LocalDateTime createdAt = LocalDateTime.now().minusDays(1);

    Task task = Task.builder()
            .id(10L)
            .title("Mapped Task")
            .description("Mapped Description")
            .dueDate(dueDate)
            .createdAt(createdAt)
            .status(TaskStatus.PENDING)
            .userId(5L)
            .build();

    TaskResponseDto dto = taskMapper.toResponseDto(task);

    assertNotNull(dto);
    assertEquals(10L, dto.getId());
    assertEquals("Mapped Task", dto.getTitle());
    assertEquals("Mapped Description", dto.getDescription());
    assertEquals(dueDate, dto.getDueDate());
    assertEquals(TaskStatus.PENDING, dto.getStatus());
    assertEquals(createdAt, dto.getCreatedAt());
    assertEquals(5L, dto.getUserId());
  }

  @Test
  void toResponseDto_ShouldNotFail_WhenOptionalFieldsAreNull() {
    Task task = Task.builder()
            .id(1L)
            .title("Title")
            .description(null)
            .dueDate(null)
            .createdAt(null)
            .status(TaskStatus.PENDING)
            .userId(null)
            .build();

    TaskResponseDto dto = taskMapper.toResponseDto(task);

    assertNotNull(dto);
    assertEquals(1L, dto.getId());
    assertEquals("Title", dto.getTitle());
    assertNull(dto.getDescription());
    assertNull(dto.getDueDate());
    assertNull(dto.getCreatedAt());
    assertEquals(TaskStatus.PENDING, dto.getStatus());
    assertNull(dto.getUserId());
  }
}
