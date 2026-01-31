package com.example.tasktracker.services;


import com.example.tasktracker.dtos.in.UserRequestDto;
import com.example.tasktracker.dtos.out.ApiResponseDto;

/**
 * Service interface for managing user-related operations in the Task Tracker application.
 *
 * <p>This interface defines methods for retrieving, validating, and storing
 * user data, such as authentication and profile management.</p>
 *
 * <p>Implementations of this interface should provide the business logic
 * for interacting with the underlying user data source.</p>
 *
 * @author Your Name
 * @since 1.0
 */
public interface UserService {

  /**
   * Register a new user based on the provided registration request.
   * @param userRequestDto containing user details
   * @return the SuccessResponse
   */
  ApiResponseDto registerUser(UserRequestDto userRequestDto);
  /**
   * Login user based on the provided Login request.
   * @param userRequestDto containing user details
   * @return the SuccessResponse
   */
  ApiResponseDto loginUser(UserRequestDto userRequestDto);
}
