package com.example.tasktracker.dtos.out;

import lombok.*;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * DTO for returning paginated task responses.
 *
 * This class avoids exposing internal mutable state by making
 * defensive copies of the task list when setting or returning it.
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class PaginatedTaskResponseDto {

  /**
   * List of tasks for the current page.
   * Stored as a defensive copy to prevent external modification.
   */
  private List<TaskResponseDto> tasks = new ArrayList<>();

  /**
   * Current page number.
   */
  private int currentPage;

  /**
   * Number of tasks per page.
   */
  private int pageSize;

  /**
   * Total number of tasks across all pages.
   */
  private long totalElements;

  /**
   * Total number of pages available.
   */
  private int totalPages;


  /**
   * Constructor with defensive copying of the task list.
   *
   * Creates a new PaginatedTaskResponseDTO with the given tasks and pagination details.
   * The tasks list is defensively copied to prevent external modification.
   *
   * @param tasks        list of tasks for the current page; a defensive copy is made
   * @param currentPage  current page number (0-based index)
   * @param pageSize     number of tasks per page
   * @param totalElements total number of tasks across all pages
   * @param totalPages   total number of pages available
   */
  public PaginatedTaskResponseDto(final List<TaskResponseDto> tasks, final int currentPage,
                                  final int pageSize, final long totalElements, final int totalPages) {
    this.tasks = new ArrayList<>(tasks);
    this.currentPage = currentPage;
    this.pageSize = pageSize;
    this.totalElements = totalElements;
    this.totalPages = totalPages;
  }


  /**
   * Getter for the task list.
   *
   * Returns an unmodifiable copy of the internal task list to prevent
   * external modification of the internal representation.
   *
   * @return an unmodifiable list of tasks; never null
   */
  public List<TaskResponseDto> getTasks() {
    return Collections.unmodifiableList(new ArrayList<>(tasks));
  }

  /**
   * Setter for the task list.
   * @param tasks tasks list
   */
  public void setTasks(final List<TaskResponseDto> tasks) {
    this.tasks = new ArrayList<>(tasks);
  }


}
