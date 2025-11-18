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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@Builder
public class Task {

  /** Unique identifier for the task. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /** Title of the task (cannot be null). */
  @Column(nullable = false)
  private String title;

  /** Detailed description of the task. */
  private String description;

  /** The date by which the task should be completed. */
  private LocalDate dueDate;

  /** Current status of the task (e.g., PENDING, COMPLETED). */
  @Enumerated(EnumType.STRING)
  private TaskStatus status = TaskStatus.PENDING;

  /** The user to whom this task belongs. */
  @Column(name = "user_id", nullable = false)
  private Long userId;

  /** Timestamp indicating when the task was created. */
  private LocalDateTime createdAt = LocalDateTime.now();

  @Override
  public String toString() {
    return "Task [id=" + id + ", title=" +title+ ", description=" +description+
            ",dueDate="+dueDate+ ", status="+status+ ", userId=" +userId+ ", createdAt=" +createdAt+ "]";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Task task = (Task) o;
    return Objects.equals(id, task.id) &&
            Objects.equals(title, task.title) &&
            Objects.equals(description, task.description) &&
            Objects.equals(dueDate, task.dueDate) &&
            Objects.equals(status, task.status) &&
            Objects.equals(userId, task.userId) &&
            Objects.equals(createdAt, task.createdAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, description, dueDate, status, userId, createdAt);

  }

    /**
     * Sets default values before the entity is persisted.
     *
     * <p>This method ensures that the task status is set to {@code PENDING} and the
     * creation timestamp is set to the current time if they are not already initialized.</p>
     *
     * <p>Annotated with {@link PrePersist}, this method is automatically called
     * by the JPA provider before the entity is inserted into the database.</p>
     */
  @PrePersist
  public void ensureDefaultStatus() {
    if (status == null) {
      status = TaskStatus.PENDING;
    }

    if (createdAt == null) {
      createdAt = LocalDateTime.now();
    }
  }

}
