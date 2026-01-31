package com.example.tasktracker.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.PrePersist;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import lombok.*;

/**
 * Entity representing a task in the system.
 * Each task is associated with a user and contains details such as title,
 * description, due date, and status.
 *
 * <p>Tasks are persisted in the {@code tasks} table.</p>
 *
 * @author Your Name
 * @since 1.0
 */
@Entity
@Table(name = "tasks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Task {

  /**
   * Task ID.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long taskId;
  /**
   * Task Title.
   */
  @Column(name = "title", unique = true)
  private String title;
  /**
   * Task Description.
   */
  @Column(name = "description")
  private String description;
  /**
   * Current Status of Task.
   */
  @Enumerated(EnumType.STRING)
  private TaskStatus status = TaskStatus.PENDING;
  /**
   * Each task belongs to a user.
   */
  @Column(name = "userId")
  private Long  userId;
  /**
   * Task DueDate.
   */
  @Column(name = "dueDate")
  private LocalDate dueDate;
  /**
   * Task creation time.
   */
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;
  /**
   *  * This ensures that the 'createdAt' timestamp is automatically set when a new entity is created.
   */
  @PrePersist
  public void prePersist() {
    this.createdAt = LocalDateTime.now();

  }

}
