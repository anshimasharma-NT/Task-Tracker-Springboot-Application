package com.example.tasktracker.serviceImpl;

import com.example.tasktracker.constants.NumericConstants;
import com.example.tasktracker.constants.TaskConstants;
import com.example.tasktracker.dtos.in.TaskRequestDto;
import com.example.tasktracker.dtos.out.PaginatedTaskResponseDto;
import com.example.tasktracker.dtos.out.ApiResponseDto;
import com.example.tasktracker.entities.Task;
import com.example.tasktracker.entities.TaskStatus;
import com.example.tasktracker.repositories.TaskRepository;
import com.example.tasktracker.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private TaskRepository taskRepository;

  @InjectMocks
  private TaskServiceImpl taskService;

  private TaskRequestDto taskRequestDto;

  @BeforeEach
  void setUp() {
    taskRequestDto = new TaskRequestDto();
    taskRequestDto.setTitle("Test Task");
    taskRequestDto.setDescription("Test Description");
    taskRequestDto.setUserId(1L);
    taskRequestDto.setDueDate(LocalDate.now().plusDays(1).format(java.time.format.DateTimeFormatter.ofPattern(NumericConstants.DATE_PATTERN)));
  }

  @Test
  void testAddTaskSuccess() {
    ApiResponseDto response = taskService.addTask(taskRequestDto);

    assertTrue(response.isSuccess());
    assertEquals(TaskConstants.TASK_ADDED_SUCCESS_MESSAGE, response.getMessage());
    verify(taskRepository).save(any(Task.class));
  }


  @Test
  void testMarkTaskAsComplete() {
    Task task = new Task();
    task.setStatus(TaskStatus.PENDING);

    ApiResponseDto response = taskService.markTaskAsComplete(task);

    assertTrue(response.isSuccess());
    assertEquals("Task marked as completed.", response.getMessage());
    assertEquals(TaskStatus.COMPLETED, task.getStatus());
    verify(taskRepository).save(task);
  }

  @Test
  void testDeleteTask() {
    Long taskId = 1L;
    ApiResponseDto response = taskService.deleteTask(taskId);

    assertTrue(response.isSuccess());
    assertEquals("Task deleted successfully.", response.getMessage());
    verify(taskRepository).deleteById(taskId);
  }

  @Test
  void testGetTasksByUser() {
    Task task = new Task();
    task.setTaskId(1L);
    task.setTitle("Task1");
    task.setDescription("Desc1");
    task.setStatus(TaskStatus.PENDING);
    task.setUserId(1L);
    task.setDueDate(LocalDate.now());

    Page<Task> page = new PageImpl<>(Collections.singletonList(task));
    when(taskRepository.findByUserId(anyLong(), any(Pageable.class))).thenReturn(page);

    PaginatedTaskResponseDto response = taskService.getTasksByUser(1L, Pageable.unpaged(), null, null);

    assertEquals(1, response.getTasks().size());
    assertEquals(task.getTitle(), response.getTasks().get(0).getTitle());
    verify(taskRepository).findByUserId(1L, Pageable.unpaged());
  }
}
