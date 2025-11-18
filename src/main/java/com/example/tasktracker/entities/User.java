package com.example.tasktracker.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.Objects;


/**
 * Entity representing a user in the system.
 * Each user has a name, email, and password, and can own multiple tasks.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User
{

  /**
   * Maximum length of the name field.
   */
  public static final int NAME_MAX_LENGTH = 100;

  /**
   * Maximum length of the email field.
   */
  public static final int EMAIL_MAX_LENGTH = 150;

  /**
   * Maximum length of the password field.
   */
  public static final int PASSWORD_MAX_LENGTH = 255;

  /**
   * Unique identifier for the user.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * Name of the user.
   */
  @Column(name = "name", nullable = false, length = NAME_MAX_LENGTH)
  private String name;

  /**
   * Email of the user (must be unique).
   */
  @Column(name = "email", unique = true, nullable = false, length = EMAIL_MAX_LENGTH)
  private String email;

  /**
   * Encrypted password of the user.
   */
  @Column(nullable = false)
  private String password;


  /**
   * User entity constructor
   *
   * @param id       the unique identifier of the user
   * @param email    the email address of the user
   * @param password the password for the user
   *
   */
  public User(long id, String name, String email, String password) {
    super();
    this.id = id;
    this.name = name;
    this.email = email;
    this.password = password;
  }

  @Override
  public String toString() {
    return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + "]";
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, email, password);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return Objects.equals(id, user.id) &&
            Objects.equals(name, user.name) &&
            Objects.equals(email, user.email) &&
            Objects.equals(password, user.password);
  }
}
