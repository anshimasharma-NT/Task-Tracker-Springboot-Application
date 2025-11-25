package com.example.tasktracker.controllers;

import com.example.tasktracker.constants.TaskConstants;
import com.example.tasktracker.constants.UrlConstants;
import com.example.tasktracker.dtos.in.TaskRequestDto;
import com.example.tasktracker.dtos.out.PaginatedTaskResponseDto;
import com.example.tasktracker.dtos.out.ApiResponseDto;
import com.example.tasktracker.dtos.out.TaskResponseDto;
import com.example.tasktracker.entities.Task;
import com.example.tasktracker.entities.TaskStatus;
import com.example.tasktracker.services.TaskService;
import com.example.tasktracker.validations.TaskValidation;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

  private MockMvc mockMvc;
  private ObjectMapper objectMapper;

  @Mock
  private TaskService taskService;

  @Mock
  private TaskValidation taskValidation;

  @InjectMocks
  private TaskController taskController;

  @BeforeEach
  void setUp() {
    objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
  }

  @Test
  void testAddTask() throws Exception {
    TaskRequestDto taskRequestDto = new TaskRequestDto();
    taskRequestDto.setTitle("Test Task");
    taskRequestDto.setDescription("Test Description");
    taskRequestDto.setUserId(1L);
    taskRequestDto.setDueDate("2025-12-31");

    ApiResponseDto responseDto = new ApiResponseDto(true, TaskConstants.TASK_ADDED_SUCCESS_MESSAGE);

    doNothing().when(taskValidation).validateAddTask(any(TaskRequestDto.class));
    when(taskService.addTask(any(TaskRequestDto.class))).thenReturn(responseDto);

    mockMvc.perform(MockMvcRequestBuilders.post(UrlConstants.TASK_ENDPOINT + UrlConstants.ADD_TASK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(taskRequestDto)))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(responseDto)))
            .andDo(MockMvcResultHandlers.print());
  }

  @Test
  void testMarkTaskAsComplete() throws Exception {
    Long taskId = 1L;
    Long userId = 1L;
    Task task = new Task();
    task.setTaskId(taskId);
    ApiResponseDto response = new ApiResponseDto(true, "Task marked as completed.");

    when(taskValidation.validateTaskID(taskId)).thenReturn(task);
    when(taskService.markTaskAsComplete(task)).thenReturn(response);

    mockMvc.perform(MockMvcRequestBuilders.put(UrlConstants.TASK_ENDPOINT + UrlConstants.MARK_TASK_AS_COMPLETED, taskId, userId)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(response)))
            .andDo(MockMvcResultHandlers.print());
  }

  @Test
  void testDeleteTask() throws Exception {
    Long taskId = 1L;
    Long userId = 1L;
    ApiResponseDto response = new ApiResponseDto(true, "Task deleted successfully.");

    when(taskValidation.validateTaskID(taskId)).thenReturn(new Task());
    when(taskService.deleteTask(taskId)).thenReturn(response);

    mockMvc.perform(MockMvcRequestBuilders.delete(UrlConstants.TASK_ENDPOINT + UrlConstants.DELETE_TASK, taskId, userId))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(response)))
            .andDo(MockMvcResultHandlers.print());
  }

  @Test
  void testGetTasksByUser() throws Exception {
    Long userId = 1L;
    int page = 0;
    int size = 10;

    LocalDate fixedDueDate = LocalDate.of(2025, 11, 23);
    LocalDateTime fixedCreatedAt = LocalDateTime.of(2025, 11, 23, 12, 0, 0);

    TaskResponseDto taskResponse = new TaskResponseDto(
            1L,
            "Task 1",
            "Description 1",
            TaskStatus.PENDING,
            fixedDueDate,
            fixedCreatedAt
    );

    List<TaskResponseDto> tasks = Collections.singletonList(taskResponse);
    PaginatedTaskResponseDto paginatedResponse = new PaginatedTaskResponseDto(
            tasks,
            page,
            size,
            1L,
            1
    );

    doNothing().when(taskValidation).validateUserID(userId);
    when(taskService.getTasksByUser(any(Long.class), any(Pageable.class), any(), any()))
            .thenReturn(paginatedResponse);

    String responseContent = mockMvc.perform(MockMvcRequestBuilders.get(UrlConstants.TASK_ENDPOINT + UrlConstants.GET_TASKS_BY_USER, userId)
                    .param("page", String.valueOf(page))
                    .param("size", String.valueOf(size))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

    PaginatedTaskResponseDto response = objectMapper.readValue(responseContent, PaginatedTaskResponseDto.class);

    assertEquals(1, response.getTasks().size());
    TaskResponseDto task = response.getTasks().get(0);
    assertEquals(1L, task.getTaskId());
    assertEquals("Task 1", task.getTitle());
    assertEquals("Description 1", task.getDescription());
    assertEquals(TaskStatus.PENDING, task.getStatus());
    assertEquals(fixedDueDate, task.getDueDate());
    assertEquals(fixedCreatedAt, task.getCreatedAt());
  }

}
