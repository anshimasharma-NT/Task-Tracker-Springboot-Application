package com.example.tasktracker.controllers;

import com.example.tasktracker.constants.UrlConstants;
import com.example.tasktracker.constants.UserConstants;
import com.example.tasktracker.dtos.in.UserRequestDto;
import com.example.tasktracker.dtos.out.ApiResponseDto;
import com.example.tasktracker.services.UserService;
import com.example.tasktracker.validations.UserValidation;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping(UrlConstants.USER_ENDPOINT)
public class UserController {
  /**
   * Logger for AuthController.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

  /**
   * Service for user-related operations.
   */
  @Autowired
  private UserService userService;
  /**
   * Injects the RegisterRequestValidator.
   */
  @Autowired
  private UserValidation userValidation;

  /**
   * Endpoint to register a new user in the system.
   *
   * @param userRequestDto the registration
   * @return {@link ResponseEntity} containing a {@link ApiResponseDto} with registration result
   */
  @PostMapping(UrlConstants.REGISTER)
  public ResponseEntity<ApiResponseDto> registerUser(@Valid @RequestBody final UserRequestDto userRequestDto) {
    LOGGER.info(UserConstants.RECEIVED_REQUEST_FOR_REGISTER, userRequestDto.getEmail());
    userValidation.registerValidate(userRequestDto);
    ApiResponseDto response = userService.registerUser(userRequestDto);
    LOGGER.info(UserConstants.USER_REGISTERED_SUCCESSFULLY, userRequestDto.getEmail());
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
  /**
   * Handles user login requests.
   * @param userRequestDto the login request
   * @return ResponseEntity containing LoginOutDTO
   */
  @PostMapping(UrlConstants.LOGIN)
  public ResponseEntity<ApiResponseDto> loginUser(@Valid @RequestBody final UserRequestDto userRequestDto) {
    LOGGER.info(UserConstants.RECEIVED_REQUEST_FOR_LOGIN, userRequestDto.getEmail());
    userValidation.loginValidate(userRequestDto);
    ApiResponseDto response = userService.loginUser(userRequestDto);
    LOGGER.info(UserConstants.USER_LOGIN_SUCCESSFULLY, userRequestDto.getEmail());
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

}
