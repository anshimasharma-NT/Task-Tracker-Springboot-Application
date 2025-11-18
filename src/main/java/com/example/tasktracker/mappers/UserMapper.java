package com.example.tasktracker.mappers;

import com.example.tasktracker.dtos.in.UserRequestDto;
import com.example.tasktracker.dtos.out.UserResponseDTo;
import com.example.tasktracker.entities.User;
import org.springframework.stereotype.Component;

/**
 * Mapper class responsible for converting between
 * {@link User}, {@link UserRequestDto}, and {@link UserResponseDTo}.
 *
 * <p>This class is annotated with {@link Component} so that it can
 * be automatically discovered and injected where needed.</p>
 */
@Component
public class UserMapper {

  /**
   * Converts a {@link UserRequestDto} into a {@link User} entity.
   *
   * @param dto the {@link UserRequestDto} containing user registration or login data;
   *            may be {@code null}
   * @return a {@link User} entity built from the provided DTO,
   *         or {@code null} if the DTO is {@code null}
   */
  public User toEntity(final UserRequestDto dto) {
    if (dto == null) {
      return null;
    }

    return User.builder()
            .name(dto.getName())
            .email(dto.getEmail())
            // Convert char[] → String correctly
            .password(dto.getPassword() != null ? new String(dto.getPassword()) : null)
            .build();
  }

  /**
   * Converts a {@link User} entity into a {@link UserResponseDTo}.
   *
   * @param user the {@link User} entity to convert; may be {@code null}
   * @return a {@link UserResponseDTo} containing non-sensitive user data,
   *         or {@code null} if the entity is {@code null}
   */
  public UserResponseDTo toResponseDto(final User user) {
    if (user == null) {
      return null;
    }

    return new UserResponseDTo(
            user.getId(),
            user.getName(),
            user.getEmail()
    );
  }
}
