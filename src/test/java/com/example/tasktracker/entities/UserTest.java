package com.example.tasktracker.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class UserTest {

  @Test
  public void testGetterAndSetter() {
    User user = new User();

    // Test id
    assertNull(user.getId());
    Long id = 1L;
    user.setId(id);
    assertEquals(id, user.getId());

    // Test name
    assertNull(user.getName());
    String name = "John Doe";
    user.setName(name);
    assertEquals(name, user.getName());

    // Test email
    assertNull(user.getEmail());
    String email = "john@example.com";
    user.setEmail(email);
    assertEquals(email, user.getEmail());

    // Test password
    assertNull(user.getPassword());
    String password = "mypassword";
    user.setPassword(password);
    assertEquals(password, user.getPassword());
  }

  @Test
  public void testParameterizedConstructor() {
    Long id = 1L;
    String email = "john@example.com";
    String password = "mypassword";
    String name = "John Sharma";

    User user = new User(id, name, email, password);
    assertEquals(id, user.getId());
    assertEquals(email, user.getEmail());
    assertEquals(password, user.getPassword());
    assertEquals(name, user.getName());
  }

  @Test
  public void testBuilderAndToString() {
    User user = User.builder()
            .id(1L)
            .name("John Doe")
            .email("john@example.com")
            .password("mypassword")
            .build();

    assertEquals(1L, user.getId());
    assertEquals("John Doe", user.getName());
    assertEquals("john@example.com", user.getEmail());
    assertEquals("mypassword", user.getPassword());

    // Just verify that toString() is not null
    assertNotNull(user.toString());
  }
}
