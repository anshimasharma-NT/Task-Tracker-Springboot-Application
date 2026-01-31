package com.example.tasktracker.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.tasktracker.constants.UserConstants;
import com.example.tasktracker.constants.UrlConstants;
import com.example.tasktracker.dtos.in.UserRequestDto;
import com.example.tasktracker.dtos.out.ApiResponseDto;
import com.example.tasktracker.exceptions.custom.AlreadyExistsException;
import com.example.tasktracker.exceptions.custom.NotFoundException;
import com.example.tasktracker.exceptions.handler.GlobalExceptionHandler;
import com.example.tasktracker.services.UserService;
import com.example.tasktracker.validations.UserValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link UserController}.
 */
@ExtendWith(MockitoExtension.class)
class UserControllerTest {
  /**
   * Used to perform HTTP requests against the controller in test.
   */
  private MockMvc mockMvc;
  /**
   *  Used to convert Java objects to JSON and vice versa.
   */
  private ObjectMapper objectMapper;
  /**
   *  Mocked service layer for user operations.
   */
  @Mock
  private UserService userService;
  /**
   * Mocked validator for registration requests.
   */
  @Mock
  private UserValidation userValidation;
  /**
   *  The controller under test.
   */
  @InjectMocks
  private UserController userController;

  /**
   * Sets up MockMvc before each test.
   */
  @BeforeEach
  void setup() {
    objectMapper = new ObjectMapper();
    mockMvc = MockMvcBuilders.standaloneSetup(userController)
            .setControllerAdvice(new GlobalExceptionHandler())
            .build();
  }

  /**
   * Tests the register endpoint.
   */
  @Test
  void testRegisterSuccessful() throws Exception {
    String name1 = "Anshima Sharma";
    String email = "anshima09@nucleusteq.com";
    String rawPassword = "$2a$10$ogezBB3qrxm6m5aagX.Nm.c72aJF3l5O5z9G90bFCxqNe0LtC4Z72";
    char[] password = rawPassword.toCharArray();

    UserRequestDto userRequestDto = new UserRequestDto(name1, email, password);
    ApiResponseDto successResponse =
            new ApiResponseDto(true, UserConstants.USER_REGISTRATION_SUCCESS_MESSAGE);

    String inputJSON = objectMapper.writeValueAsString(userRequestDto);
    String expectedOutputJSON = objectMapper.writeValueAsString(successResponse);

    doNothing().when(userValidation).registerValidate(any(UserRequestDto.class));

    when(userService.registerUser(any(UserRequestDto.class))).thenReturn(successResponse);

    mockMvc.perform(
                    MockMvcRequestBuilders.post(UrlConstants.USER_ENDPOINT + UrlConstants.REGISTER)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(inputJSON))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.content().json(expectedOutputJSON))
            .andDo(MockMvcResultHandlers.print());
  }
  /**
   * Tests the register already exist.
   */
  @Test
  void testRegisterUserWithExistingEmail() throws Exception {
    String name1 = "Anshima Sharma";
    String email = "anshima09@nucleusteq.com";
    String rawPassword = "$2a$10$ogezBB3qrxm6m5aagX.Nm.c72aJF3l5O5z9G90bFCxqNe0LtC4Z72";
    char[] password = rawPassword.toCharArray();

    UserRequestDto userRequestDto = new UserRequestDto(name1, email, password);
    String inputJSON = objectMapper.writeValueAsString(userRequestDto);

    doNothing().when(userValidation).registerValidate(any(UserRequestDto.class));
    when(userService.registerUser(any(UserRequestDto.class))).thenThrow(new AlreadyExistsException("Email already in use"));
    ApiResponseDto expectedResponse =
            new ApiResponseDto(false, "Email already in use");
    String expectedJson = objectMapper.writeValueAsString(expectedResponse);

    mockMvc.perform(
                    MockMvcRequestBuilders.post(UrlConstants.USER_ENDPOINT + UrlConstants.REGISTER)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(inputJSON))
            .andExpect(MockMvcResultMatchers.status().isConflict())
            .andExpect(MockMvcResultMatchers.content().json(expectedJson))
            .andDo(MockMvcResultHandlers.print());
  }
  /**
   * Test Login Endpoint.
   */
  @Test
  public void testLoginUser() throws Exception {
    String email = "anshima09@nucleusteq.com";
    String rawPassword = "$2a$10$ogezBB3qrxm6m5aagX.Nm.c72aJF3l5O5z9G90bFCxqNe0LtC4Z72";
    char[] password = rawPassword.toCharArray();
    UserRequestDto userRequestDto = new UserRequestDto(email, password);
    String inputJSON = objectMapper.writeValueAsString(userRequestDto);

    doNothing().when(userValidation).loginValidate(any(UserRequestDto.class));
    ApiResponseDto successResponse =
            new ApiResponseDto(true, UserConstants.USER_LOGIN_SUCCESSFULLY);
    when(userService.loginUser(any(UserRequestDto.class))).thenReturn(successResponse);

    String expectedOutputJSON = objectMapper.writeValueAsString(successResponse);

    mockMvc.perform(
                    MockMvcRequestBuilders.post(UrlConstants.USER_ENDPOINT + UrlConstants.LOGIN)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(inputJSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(expectedOutputJSON))
            .andDo(MockMvcResultHandlers.print());
  }
  /**
   * Tests the login user not exist.
   */
  @Test
  public void testLoginUserNotExist() throws Exception {
    String email = "anshima09@nucleusteq.com";
    String rawPassword = "$2a$10$ogezBB3qrxm6m5aagX.Nm.c72aJF3l5O5z9G90bFCxqNe0LtC4Z72";
    char[] password = rawPassword.toCharArray();
    UserRequestDto userRequestDto = new UserRequestDto(email, password);
    String inputJSON = objectMapper.writeValueAsString(userRequestDto);
    doNothing().when(userValidation).loginValidate(any(UserRequestDto.class));
    ApiResponseDto expectedResponse =
            new ApiResponseDto(false, "User not found");
    String expectedOutputJSON = objectMapper.writeValueAsString(expectedResponse);
    when(userService.loginUser(any(UserRequestDto.class))).thenThrow(new NotFoundException("User not found"));
    mockMvc.perform(
                    MockMvcRequestBuilders.post(UrlConstants.USER_ENDPOINT + UrlConstants.LOGIN)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(inputJSON))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.content().json(expectedOutputJSON))
            .andDo(MockMvcResultHandlers.print());
  }
}
