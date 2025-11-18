package com.example.tasktracker.serviceImpl;

import com.example.tasktracker.entities.Task;
import com.example.tasktracker.entities.TaskStatus;
import com.example.tasktracker.entities.User;
import com.example.tasktracker.repositories.TaskRepository;
import com.example.tasktracker.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceImplTest {

  @Mock
  private TaskRepository taskRepository;

  @Mock
  private UserService userService;

  @InjectMocks
  private TaskServiceImpl taskService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  // ---------------------------------------------------------------------------
  // createTask()
  // ---------------------------------------------------------------------------

  @Test
  void createTask_ShouldSetUserId_AndSaveTask() {
    Task task = new Task();
    when(taskRepository.save(task)).thenReturn(task);

    Task result = taskService.createTask(task, 10L);

    assertEquals(10L, task.getUserId());
    assertEquals(task, result);
    verify(taskRepository, times(1)).save(task);
  }

  // ---------------------------------------------------------------------------
  // getTaskByIdAndUser()
  // ---------------------------------------------------------------------------

  @Test
  void getTaskByIdAndUser_ShouldReturnTask_WhenFound() {
    Task task = new Task();
    when(taskRepository.findByIdAndUserId(1L, 5L))
            .thenReturn(Optional.of(task));

    Task result = taskService.getTaskByIdAndUser(1L, 5L);

    assertNotNull(result);
    assertEquals(task, result);
  }

  @Test
  void getTaskByIdAndUser_ShouldReturnNull_WhenNotFound() {
    when(taskRepository.findByIdAndUserId(1L, 5L))
            .thenReturn(Optional.empty());

    Task result = taskService.getTaskByIdAndUser(1L, 5L);

    assertNull(result);
  }

  // ---------------------------------------------------------------------------
  // markTaskAsCompleted()
  // ---------------------------------------------------------------------------

  @Test
  void markTaskAsCompleted_ShouldUpdateStatus_WhenUserOwnsTask() {
    Task task = new Task();
    task.setUserId(20L);

    when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
    when(taskRepository.save(task)).thenReturn(task);

    Task result = taskService.markTaskAsCompleted(1L, 20L);

    assertEquals(TaskStatus.COMPLETED, result.getStatus());
    verify(taskRepository, times(1)).save(task);
  }

  @Test
  void markTaskAsCompleted_ShouldThrow_WhenTaskNotFound() {
    when(taskRepository.findById(1L)).thenReturn(Optional.empty());

    IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> taskService.markTaskAsCompleted(1L, 20L)
    );

    assertTrue(ex.getMessage().contains("Task not found"));
  }

  @Test
  void markTaskAsCompleted_ShouldThrow_WhenUserNotOwner() {
    Task task = new Task();
    task.setUserId(99L);

    when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

    IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> taskService.markTaskAsCompleted(1L, 20L)
    );

    assertTrue(ex.getMessage().contains("not allowed"));
  }

  // ---------------------------------------------------------------------------
  // deleteTaskByUser()
  // ---------------------------------------------------------------------------

  @Test
  void deleteTaskByUser_ShouldReturnFalse_WhenUserNotFound() {
    when(userService.getById(5L)).thenReturn(null);

    boolean result = taskService.deleteTaskByUser(5L, 1L);

    assertFalse(result);

    // ❌ Removed incorrect verify(taskService...) call
    // Because taskService is NOT a mock
  }

  @Test
  void deleteTaskByUser_ShouldReturnFalse_WhenTaskNotFound() {
    User user = new User();
    when(userService.getById(5L)).thenReturn(user);
    when(taskRepository.findById(1L)).thenReturn(Optional.empty());

    boolean result = taskService.deleteTaskByUser(5L, 1L);

    assertFalse(result);
  }

  @Test
  void deleteTaskByUser_ShouldReturnFalse_WhenUserNotOwner() {
    User user = new User();
    Task task = new Task();
    task.setUserId(99L);

    when(userService.getById(5L)).thenReturn(user);
    when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

    boolean result = taskService.deleteTaskByUser(5L, 1L);

    assertFalse(result);
  }

  @Test
  void deleteTaskByUser_ShouldDeleteTask_WhenUserOwnsTask() {
    User user = new User();
    Task task = new Task();
    task.setUserId(5L);

    when(userService.getById(5L)).thenReturn(user);
    when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

    boolean result = taskService.deleteTaskByUser(5L, 1L);

    assertTrue(result);
    verify(taskRepository, times(1)).delete(task);
  }

  // ---------------------------------------------------------------------------
  // getTasksByUserWithFilters()
  // ---------------------------------------------------------------------------

  @Test
  void getTasksByUserWithFilters_ShouldReturnPagedResults() {
    Task task1 = new Task();
    Task task2 = new Task();

    List<Task> tasks = List.of(task1, task2);
    Page<Task> page = new PageImpl<>(tasks);

    when(taskRepository.findTasksByFilters(
            eq(10L), eq(TaskStatus.PENDING),
            eq(LocalDate.of(2025, 1, 1)),
            any(Pageable.class)
    )).thenReturn(page);

    List<Task> result = taskService.getTasksByUserWithFilters(
            10L,
            TaskStatus.PENDING,
            LocalDate.of(2025, 1, 1),
            0,
            10
    );

    assertEquals(2, result.size());
    assertEquals(task1, result.get(0));
    assertEquals(task2, result.get(1));
  }
}
