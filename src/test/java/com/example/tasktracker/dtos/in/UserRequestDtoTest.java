package com.example.tasktracker.dtos.in;


import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class UserRequestDtoTest {

  /**
   * Verifies that fields can be set and retrieved, and password getter returns a copy.
   */
  @Test
  public void testSetterAndGetter() {
    UserRequestDto userRequestDto = new UserRequestDto();

    assertNull(userRequestDto.getName());
    userRequestDto.setName("Anshima Sharma");
    assertEquals("Anshima Sharma",userRequestDto.getName());

    assertNull(userRequestDto.getEmail());
    userRequestDto.setEmail("anshima@nucleusteq.com");
    assertEquals("anshima@nucleusteq.com", userRequestDto.getEmail());

    assertNull(userRequestDto.getPassword());
    char[] original = "secret".toCharArray();
    userRequestDto.setPassword(original);
    char[] returned = userRequestDto.getPassword();
    assertArrayEquals(original, returned);
    assertNotSame(original, returned);
  }

  @Test
  public void testToString() {
    String name = "Isis Sharma";
    String email = "isis@nucleusteq.com";
    String rawPassword = "$2a$10$ogezBB3qrxm6m5aagX.Nm.c72aJF3l5O5z9G90bFCxqNe0LtC4Z72";
    char[] password = rawPassword.toCharArray();
    UserRequestDto userRequestDto = new UserRequestDto(name, email, password);

    String expected =
            "UserRequestDto(name=Isis Sharma, email=isis@nucleusteq.com, password=[$, 2, a, $, 1, 0, $, o, g, e, z, B, B, 3, q, r, x, m, 6, m, 5, a, a, g, X, ., N, m, ., c, 7, 2, a, J, F, 3, l, 5, O, 5, z, 9, G, 9, 0, b, F, C, x, q, N, e, 0, L, t, C, 4, Z, 7, 2])";

    assertEquals(expected, userRequestDto.toString());
  }

  /**
   * Testing EqualsAndHashCode.
   */
  @Test
  public void testEqualsAndHashCode() {
    String name = "Disha Bundela";
    String email = "disha12@nucleusteq.com";
    String rawPassword = "$2a$10$ogezBB3qrxm6m5aagX.Nm.c72aJF3l5O5z9G90bFCxqNe0LtC4Z72";
    char[] password = rawPassword.toCharArray();
    UserRequestDto userRequestDto1 = new UserRequestDto(name, email, password);

    assertEquals(userRequestDto1, userRequestDto1);
    assertEquals(userRequestDto1.hashCode(), userRequestDto1.hashCode());
    assertNotEquals(new Object(), userRequestDto1);

    UserRequestDto userRequestDto2 = new UserRequestDto(name, email, password);
    assertEquals(userRequestDto1, userRequestDto2);
    assertEquals(userRequestDto1.hashCode(), userRequestDto2.hashCode());
    String email2 = "dishu@nucleusteq.com";
    UserRequestDto userRequestDto3 = new UserRequestDto(name, email2, password);
    assertNotEquals(userRequestDto1, userRequestDto3);
    assertNotEquals(userRequestDto1.hashCode(), userRequestDto3.hashCode());
  }

}
