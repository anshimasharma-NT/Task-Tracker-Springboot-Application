package com.example.tasktracker.dtos.out;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PaginatedTaskResponseDtoTest {

  @Test
  void testConstructorAndGetters() {
    List<TaskResponseDto> tasks = new ArrayList<>();
    tasks.add(new TaskResponseDto(1L, "Task1", "Desc1", null, null, null));
    tasks.add(new TaskResponseDto(2L, "Task2", "Desc2", null, null, null));

    PaginatedTaskResponseDto dto = new PaginatedTaskResponseDto(tasks, 0, 10, 50, 5);

    // Defensive copy test
    assertNotSame(tasks, dto.getTasks());
    assertEquals(tasks, dto.getTasks());

    assertEquals(0, dto.getCurrentPage());
    assertEquals(10, dto.getPageSize());
    assertEquals(50, dto.getTotalElements());
    assertEquals(5, dto.getTotalPages());

    // Modifying original list should not affect DTO
    tasks.add(new TaskResponseDto(3L, "Task3", "Desc3", null, null, null));
    assertEquals(2, dto.getTasks().size());
  }

  @Test
  void testSetterAndDefensiveCopy() {
    PaginatedTaskResponseDto dto = new PaginatedTaskResponseDto();
    List<TaskResponseDto> tasks = new ArrayList<>();
    tasks.add(new TaskResponseDto(1L, "Task1", "Desc1", null, null, null));

    dto.setTasks(tasks);

    // Defensive copy test
    assertNotSame(tasks, dto.getTasks());
    assertEquals(tasks, dto.getTasks());

    // Modifying returned list should throw exception
    assertThrows(UnsupportedOperationException.class, () -> dto.getTasks().add(new TaskResponseDto()));
  }

  @Test
  void testEqualsAndHashCode() {
    List<TaskResponseDto> tasks1 = new ArrayList<>();
    tasks1.add(new TaskResponseDto(1L, "Task1", "Desc1", null, null, null));

    List<TaskResponseDto> tasks2 = new ArrayList<>();
    tasks2.add(new TaskResponseDto(1L, "Task1", "Desc1", null, null, null));

    PaginatedTaskResponseDto dto1 = new PaginatedTaskResponseDto(tasks1, 0, 10, 50, 5);
    PaginatedTaskResponseDto dto2 = new PaginatedTaskResponseDto(tasks2, 0, 10, 50, 5);

    assertEquals(dto1, dto2);
    assertEquals(dto1.hashCode(), dto2.hashCode());

    PaginatedTaskResponseDto dto3 = new PaginatedTaskResponseDto(new ArrayList<>(), 1, 5, 10, 2);
    assertNotEquals(dto1, dto3);
    assertNotEquals(dto1.hashCode(), dto3.hashCode());
  }

  @Test
  void testToString() {
    List<TaskResponseDto> tasks = new ArrayList<>();
    tasks.add(new TaskResponseDto(1L, "Task1", "Desc1", null, null, null));

    PaginatedTaskResponseDto dto = new PaginatedTaskResponseDto(tasks, 0, 10, 50, 5);
    String str = dto.toString();

    assertTrue(str.contains("tasks="));
    assertTrue(str.contains("currentPage=0"));
    assertTrue(str.contains("pageSize=10"));
    assertTrue(str.contains("totalElements=50"));
    assertTrue(str.contains("totalPages=5"));
  }
}
