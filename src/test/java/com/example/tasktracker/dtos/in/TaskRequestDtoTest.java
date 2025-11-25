package com.example.tasktracker.dtos.in;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link TaskRequestDto}.
 */
public class TaskRequestDtoTest {

  /**
   * Tests getters and setters.
   */
  @Test
  public void testSetterAndGetter() {

    TaskRequestDto dto = new TaskRequestDto();

    assertNull(dto.getTitle());
    dto.setTitle("Build API");
    assertEquals("Build API", dto.getTitle());

    assertNull(dto.getDescription());
    dto.setDescription("API development work");
    assertEquals("API development work", dto.getDescription());

    assertNull(dto.getUserId());
    dto.setUserId(5L);
    assertEquals(5L, dto.getUserId());

    assertNull(dto.getDueDate());
    dto.setDueDate("12-25-2025");
    assertEquals("12-25-2025", dto.getDueDate());
  }

  /**
   * Tests toString() method.
   */
  @Test
  public void testToString() {
    TaskRequestDto dto = new TaskRequestDto();
    dto.setTitle("Task Title");
    dto.setDescription("Task description");
    dto.setUserId(10L);
    dto.setDueDate("11-20-2025");

    String expected = "TaskRequestDto(title=Task Title, description=Task description, " +
            "userId=10, dueDate=11-20-2025)";

    assertEquals(expected, dto.toString());
  }

  /**
   * Tests equals() and hashCode().
   */
  @Test
  public void testEqualsAndHashCode() {

    TaskRequestDto dto1 = new TaskRequestDto();
    dto1.setTitle("Task A");
    dto1.setDescription("Desc");
    dto1.setUserId(1L);
    dto1.setDueDate("10-15-2025");

    TaskRequestDto dto2 = new TaskRequestDto();
    dto2.setTitle("Task A");
    dto2.setDescription("Desc");
    dto2.setUserId(1L);
    dto2.setDueDate("10-15-2025");

    TaskRequestDto dto3 = new TaskRequestDto();
    dto3.setTitle("Another Task");
    dto3.setDescription("Another Desc");
    dto3.setUserId(2L);
    dto3.setDueDate("11-11-2025");

    assertEquals(dto1, dto2);
    assertEquals(dto1.hashCode(), dto2.hashCode());

    assertNotEquals(dto1, dto3);
    assertNotEquals(dto1.hashCode(), dto3.hashCode());

    assertNotEquals(dto1, new Object());
  }
}
