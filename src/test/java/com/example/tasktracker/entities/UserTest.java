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
  public void testToString() {
    User user = new User();
    Long id = 1L;
    String name = "Anshima Sharma";
    String email = "anshima0906@gmail.com";
    String password = "Anshima0906";

    user.setId(id);
    user.setName(name);
    user.setEmail(email);
    user.setPassword(password);

    String expected = "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + "]";
    assertEquals(expected, user.toString());
  }

  @Test
  public void testEqualsAndHashCode(){
    Long id = 1L;
    String name = "Anshima Sharma";
    String email = "anshima0906@gmail.com";
    String password = "Anshima0906";

    User user1 = setUpUser(id, name, email, password);

    assertEquals(user1, user1);
    assertEquals(user1.hashCode(), user1.hashCode());

    assertNotEquals(new Object(), user1);

    User user2 = setUpUser(id, name, email, password);
    assertEquals(user1, user2);
    assertEquals(user1.hashCode(), user2.hashCode());

    user2 = setUpUser(2L, name, email, password);
    assertNotEquals(user1, user2);
    assertNotEquals(user1.hashCode(), user2.hashCode());

    user2 = setUpUser(id, "Garima Sharma", email, password);
    assertNotEquals(user1, user2);
    assertNotEquals(user1.hashCode(), user2.hashCode());

    user2 = setUpUser(id, name, "anshima54@gmail.com", password);
    assertNotEquals(user1, user2);
    assertNotEquals(user1.hashCode(), user2.hashCode());

    user2 = setUpUser(id, name, email, "Anshima54");
    assertNotEquals(user1, user2);
    assertNotEquals(user1.hashCode(), user2.hashCode());

    user1 = new User();
    user2 = new User();
    assertEquals(user1, user2);
    assertEquals(user1.hashCode(), user2.hashCode());
  }

  private User setUpUser(Long id, String name, String email, String password) {
    User user = new User();
    user.setId(id);
    user.setName(name);
    user.setEmail(email);
    user.setPassword(password);
    return user;
  }
}
