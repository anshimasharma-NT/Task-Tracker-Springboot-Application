package com.example.tasktracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration class for the Task Tracker application.
 * Configures HTTP security rules, CSRF, authentication, and password encoding.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  /**
   * Configures the security filter chain for the application.
   * Disables CSRF, sets authorization rules, and adds custom authentication filter.
   *
   * @param http HttpSecurity object
   * @return SecurityFilterChain object with configured security settings
   * @throws Exception If an error occurs during configuration
   */
  @Bean
  public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
    return http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/api/user/register", "/api/user/login",
                            "/api/task/addTask", "/api/task/*/user/*/completeTask",
                            "/api/task/*/user/*/deleteTask", "/api/task/user/*/getTasksByUser").permitAll()
                    .anyRequest().authenticated()
            )
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .build();
  }

  /**
   * Bean to provide a BCryptPasswordEncoder for password encoding.
   *
   * @return BCryptPasswordEncoder object
   */
  @Bean
  public static PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
