package com.example.tasktracker.dtos.in;

import com.example.tasktracker.constants.NumericConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Arrays;
import java.util.Objects;

/**
 * Data Transfer Object for user registration and login requests.
 *
 * <p>This class defines validation constraints on input fields to ensure
 * that the user data received from the client meets the application's
 * business and security requirements.</p>
 *
 * <p>This class is declared as {@code final} because it is not designed
 * for inheritance — it is intended solely as a data carrier.</p>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public final class UserRequestDto {

  /** User's name. */
  private String name;

  /** User's email, required and must be in valid email format. */
  @Size(max = NumericConstants.EMAIL_MAX_LENGTH, message = "Email must be at most 150 characters")
  private String email;

  /** Password, required and must be strong enough. */
  private char[] password;

  /**
   * Defensive getter: returns a copy of the password array.
   * @return password
   */
  public char[] getPassword() {
    return password == null ? null : password.clone();
  }

  /**
   * Defensive setter: stores a copy of the password array.
   * @param password password setter
   */
  public void setPassword(final char[] password) {
    this.password = password == null ? null : password.clone();
  }

  /**
   * Custom constructor that makes defensive copy of password.
   * @param email email of the user
   * @param password password of the user
   */
  public UserRequestDto(final String email, final char[] password) {
    this.email = email;
    this.password = password == null ? null : password.clone();
  }

}
