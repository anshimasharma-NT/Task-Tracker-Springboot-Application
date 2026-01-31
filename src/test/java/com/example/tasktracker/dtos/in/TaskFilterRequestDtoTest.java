package com.example.tasktracker.dtos.in;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link TaskFilterRequestDto}.
 */
class TaskFilterRequestDtoTest {

  @Test
  void testGetterAndSetter() {
    TaskFilterRequestDto dto = new TaskFilterRequestDto();

    assertNull(dto.getUserId());
    Long userId = 100L;
    dto.setUserId(userId);
    assertEquals(userId, dto.getUserId());

    dto.setPage("2");
    assertEquals("2", dto.getPage());

    dto.setSize("50");
    assertEquals("50", dto.getSize());

    assertNull(dto.getDueDate());
    dto.setDueDate("12-31-2025");
    assertEquals("12-31-2025", dto.getDueDate());

    assertNull(dto.getStatus());
    dto.setStatus("PENDING");
    assertEquals("PENDING", dto.getStatus());
  }

  @Test
  void testToString() {
    TaskFilterRequestDto dto = new TaskFilterRequestDto();
    dto.setUserId(1L);
    dto.setPage("0");
    dto.setSize("10");
    dto.setDueDate("01-01-2025");
    dto.setStatus("COMPLETED");

    String expected = "TaskFilterRequestDto(userId=1, page=0, size=10, dueDate=01-01-2025, status=COMPLETED)";
    assertEquals(expected, dto.toString());
  }

  @Test
  void testEqualsAndHashCode() {
    TaskFilterRequestDto dto1 = new TaskFilterRequestDto();
    dto1.setUserId(1L);
    dto1.setPage("0");
    dto1.setSize("10");
    dto1.setDueDate("01-01-2025");
    dto1.setStatus("COMPLETED");

    TaskFilterRequestDto dto2 = new TaskFilterRequestDto();
    dto2.setUserId(1L);
    dto2.setPage("0");
    dto2.setSize("10");
    dto2.setDueDate("01-01-2025");
    dto2.setStatus("COMPLETED");

    TaskFilterRequestDto dto3 = new TaskFilterRequestDto();
    dto3.setUserId(2L);

    assertEquals(dto1, dto2);
    assertEquals(dto1.hashCode(), dto2.hashCode());
    assertNotEquals(dto1, dto3);
    assertNotEquals(dto1.hashCode(), dto3.hashCode());
  }
}
