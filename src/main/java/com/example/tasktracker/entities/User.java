package com.example.tasktracker.entities;

import com.example.tasktracker.constants.NumericConstants;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import lombok.*;

import java.util.Objects;


/**
 * Entity representing a user in the system.
 * Each user has a name, email, and password, and can own multiple tasks.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "users")
public class User
{



  /**
   * Unique identifier for the user.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;

  /**
   * Name of the user.
   */
  @Column(name = "name", nullable = false, length = NumericConstants.NAME_MAX_LENGTH)
  private String name;

  /**
   * Email of the user (must be unique).
   */
  @Column(name = "email", unique = true, nullable = false, length = NumericConstants.EMAIL_MAX_LENGTH)
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
    this.name = name;
    this.email = email;
    this.password = password;
  }

}
