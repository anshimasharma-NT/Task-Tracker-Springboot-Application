package com.example.tasktracker.mappers;

import com.example.tasktracker.dtos.in.TaskRequestDto;
import com.example.tasktracker.dtos.out.TaskResponseDto;
import com.example.tasktracker.entities.Task;
import com.example.tasktracker.entities.TaskStatus;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between {@link Task} entities and DTOs.
 */
@Component
public class TaskMapper {

  /**
   * Converts a {@link TaskRequestDto} into a {@link Task} entity.
   *
   * @param dto the task request DTO
   * @return the converted task entity
   */
  public Task toEntity(final TaskRequestDto dto) {
    if (dto == null) {
      return null;
    }

    return Task.builder()
            .title(dto.getTitle())
            .description(dto.getDescription())
            .dueDate(dto.getDueDate())
            .status(TaskStatus.PENDING)
            .build();
  }

  /**
   * Converts a {@link Task} entity into a {@link TaskResponseDto}.
   *
   * @param task the task entity
   * @return the response DTO containing task data
   */
  public TaskResponseDto toResponseDto(final Task task) {
    if (task == null) {
      return null;
    }

    return new TaskResponseDto(
            task.getId(),
            task.getTitle(),
            task.getDescription(),
            task.getDueDate(),
            task.getStatus(),
            task.getCreatedAt(),
            task.getUserId()
    );
  }
}
