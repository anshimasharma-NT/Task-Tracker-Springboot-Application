package com.example.tasktracker.dtos.in;

import com.example.tasktracker.constants.NumericConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public final class UserRequestDto {



  /** User's name, required and must be between 2–100 characters. */
  @NotBlank(message = "Name is required")
  @Size(min = 2, max = NumericConstants.NAME_MAX_LENGTH,
          message = "Name must be between 2 and 100 characters")
  private String name;

  /** User's email, required and must be in valid email format. */
  @NotBlank(message = "Email is required")
  @Email(message = "Invalid email format")
  @Size(max = NumericConstants.EMAIL_MAX_LENGTH, message = "Email must be at most 150 characters")
  private String email;

  /** Password, required and must be strong enough. */
  @NotBlank(message = "Password is required")
  @Size(min = NumericConstants.PASSWORD_MIN_LENGTH, max = NumericConstants.PASSWORD_MAX_LENGTH,
          message = "Password must be between 8 and 255 characters")
  private String password;

}
