package com.example.tasktracker.services;

import com.example.tasktracker.dtos.in.TaskRequestDto;
import com.example.tasktracker.dtos.out.PaginatedTaskResponseDto;
import com.example.tasktracker.dtos.out.ApiResponseDto;
import com.example.tasktracker.entities.Task;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for managing tasks in the Task Tracker application.
 *
 * <p>This interface defines methods for creating, retrieving, and managing tasks.</p>
 */
public interface TaskService {

  /**
   * Add a new task based on the provided user request.
   * @param request containing task details
   * @return the SuccessResponse
   */
  ApiResponseDto addTask(TaskRequestDto request);
  /**
   * Marks a task as completed.
   *
   * @param task of the task to be marked complete.
   * @return SuccessResponseDTO indicating success status and message.
   */
  ApiResponseDto markTaskAsComplete(Task task);
  /**
   * Deletes a task based on the provided task ID.
   *
   * @param taskId ID of the task to be deleted.
   * @return SuccessResponseDTO indicating success status and message.
   */
  ApiResponseDto deleteTask(Long taskId);
  /**
   * Retrieves a paginated list of tasks for a specific user, optionally filtered by due date and status.
   *
   * @param userId   ID of the user whose tasks are to be retrieved
   * @param pageable Pagination information
   * @param dueDate  Optional due date filter in format yyyy-MM-dd (can be null)
   * @param status   Optional task status filter (e.g., PENDING, COMPLETE) (can be null)
   * @return a {@link PaginatedTaskResponseDto} containing the tasks and pagination details
   */
  PaginatedTaskResponseDto getTasksByUser(Long userId, Pageable pageable, String dueDate, String status);
}
