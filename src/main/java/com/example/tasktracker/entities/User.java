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
public class User {

  /** Maximum length of the name field. */
  public static final int NAME_MAX_LENGTH = 100;

  /** Maximum length of the email field. */
  public static final int EMAIL_MAX_LENGTH = 150;

  /** Maximum length of the password field. */
  public static final int PASSWORD_MAX_LENGTH = 255;

  /** Unique identifier for the user. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /** Name of the user. */
  @Column(name = "name", nullable = false, length = NAME_MAX_LENGTH)
  private String name;

  /** Email of the user (must be unique). */
  @Column(name = "email", unique = true, nullable = false, length = EMAIL_MAX_LENGTH)
  private String email;

  /** Encrypted password of the user. */
  @Column(nullable = false, length = PASSWORD_MAX_LENGTH)
  private String password;


}
