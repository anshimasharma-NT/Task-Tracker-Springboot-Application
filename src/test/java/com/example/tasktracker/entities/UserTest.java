package com.example.tasktracker.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.example.tasktracker.entities.User;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link User}.
 */
class UserTest {

  /**
   * Tests getters and setters of the User entity.
   */
  @Test
  public void testGetterAndSetter() {
    User user = new User();

    assertNull(user.getUserId());
    Long userId = 1L;
    user.setUserId(userId);
    assertEquals(userId, user.getUserId());

    assertNull(user.getName());
    String name = "Anshima";
    user.setName(name);
    assertEquals(name, user.getName());

    assertNull(user.getEmail());
    String email = "anshima54@nucleusteq.com";
    user.setEmail(email);
    assertEquals(email, user.getEmail());

    assertNull(user.getPassword());
    String password = "$2a$10$ogezBB3qrxm6m5aagX.Nm.c72aJF3l5O5z9G90bFCxqNe0LtC4Z72";
    user.setPassword(password);
    assertEquals(password, user.getPassword());
  }

  /**
   * Tests the toString() method.
   */
  @Test
  public void testToString() {
    User user = new User();
    Long userId = 1L;
    String name = "Anshima Sharma";
    String email = "anshima54@nucleusteq.com";
    String password = "$2a$10$ogezBB3qrxm6m5aagX.Nm.c72aJF3l5O5z9G90bFCxqNe0LtC4Z72";

    user.setUserId(userId);
    user.setEmail(email);
    user.setName(name);
    user.setPassword(password);

    String expected = "User(userId=1, name=Anshima Sharma, email=anshima54@nucleusteq.com, "
            + "password=$2a$10$ogezBB3qrxm6m5aagX.Nm.c72aJF3l5O5z9G90bFCxqNe0LtC4Z72)";
    assertEquals(expected, user.toString());
  }

  /**
   * Tests equals() and hashCode() behavior.
   */
  @Test
  public void testEqualsAndHashCode() {
    Long userId = 1L;
    String name = "Anshima Sharma";
    String email = "anshima54@nucleusteq.com";
    String password = "$2a$10$ogezBB3qrxm6m5aagX.Nm.c72aJF3l5O5z9G90bFCxqNe0LtC4Z72";

    User user1 = new User(userId, name, email, password);

    assertEquals(user1, user1);
    assertEquals(user1.hashCode(), user1.hashCode());

    User user2 = new User(2L, name, email, password);
    assertEquals(user2, user2);
    assertEquals(user2.hashCode(), user2.hashCode());

  }
}
