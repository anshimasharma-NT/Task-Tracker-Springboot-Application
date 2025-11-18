package com.example.tasktracker.mappers;

import com.example.tasktracker.dtos.in.UserRequestDto;
import com.example.tasktracker.dtos.out.UserResponseDTo;
import com.example.tasktracker.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

  private UserMapper userMapper;

  @BeforeEach
  void setUp() {
    userMapper = new UserMapper();
  }

  // --------------------------------------------------------------------
  // toEntity() Tests
  // --------------------------------------------------------------------

  @Test
  void toEntity_ShouldReturnNull_WhenDtoIsNull() {
    User result = userMapper.toEntity(null);
    assertNull(result);
  }

  @Test
  void toEntity_ShouldMapFieldsCorrectly() {
    UserRequestDto dto = new UserRequestDto(
            "John Doe",
            "john@example.com",
            "password123".toCharArray()
    );

    User user = userMapper.toEntity(dto);

    assertNotNull(user);
    assertEquals("John Doe", user.getName());
    assertEquals("john@example.com", user.getEmail());
    assertEquals("password123", user.getPassword());
  }

  // --------------------------------------------------------------------
  // toResponseDto() Tests
  // --------------------------------------------------------------------

  @Test
  void toResponseDto_ShouldReturnNull_WhenEntityIsNull() {
    UserResponseDTo result = userMapper.toResponseDto(null);
    assertNull(result);
  }

  @Test
  void toResponseDto_ShouldMapFieldsCorrectly() {
    User user = User.builder()
            .id(10L)
            .name("Jane Doe")
            .email("jane@example.com")
            .password("secret")
            .build();

    UserResponseDTo dto = userMapper.toResponseDto(user);

    assertNotNull(dto);
    assertEquals(10L, dto.getId());
    assertEquals("Jane Doe", dto.getName());
    assertEquals("jane@example.com", dto.getEmail());
  }

  @Test
  void toResponseDto_ShouldNotExposeSensitiveFields() {
    User user = User.builder()
            .id(99L)
            .name("Demo User")
            .email("demo@example.com")
            .password("SHOULD_NOT_BE_EXPOSED")
            .build();

    UserResponseDTo response = userMapper.toResponseDto(user);

    assertNotNull(response);

    // Response DTO has no password field, so we simply check the public fields
    assertEquals(99L, response.getId());
    assertEquals("Demo User", response.getName());
    assertEquals("demo@example.com", response.getEmail());
  }
}
