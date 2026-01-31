package com.example.tasktracker.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit test for {@link SecurityConfig}.
 */
@ExtendWith(MockitoExtension.class)
class SecurityConfigTest {

  /**
   * Mock HttpSecurity for testing security configuration.
   **/
  @Mock
  private HttpSecurity httpSecurity;

  /**
   * SecurityConfiguration instance under test.
   **/
  @InjectMocks
  private SecurityConfig securityConfiguration;

  /**
   * Mock SecurityFilterChain returned by HttpSecurity.build().
   **/
  @Mock
  private DefaultSecurityFilterChain filterChain;

  /**
   * Mock PasswordEncoder.
   **/
  @Mock
  private PasswordEncoder passwordEncoder;

  /**
   * Tests that the securityFilterChain method configures HttpSecurity
   * correctly and returns a SecurityFilterChain.
   *
   * @throws Exception if building the filter chain fails
   */
  @Test
  void securityFilterChainTest() throws Exception {
    when(httpSecurity.csrf(any())).thenReturn(httpSecurity);
    when(httpSecurity.authorizeHttpRequests(any())).thenReturn(httpSecurity);
    when(httpSecurity.sessionManagement(any())).thenReturn(httpSecurity);
    when(httpSecurity.build()).thenReturn(filterChain);

    SecurityFilterChain result = securityConfiguration.securityFilterChain(httpSecurity);

    assertNotNull(result);
    assertEquals(filterChain, result);

    verify(httpSecurity).csrf(any());
    verify(httpSecurity).authorizeHttpRequests(any());
    verify(httpSecurity).sessionManagement(any());
    verify(httpSecurity).build();
  }


  /**
   * Tests that the passwordEncoder bean returns a BCryptPasswordEncoder.
   */
  @Test
  void testPasswordEncoder() {
    PasswordEncoder encoder = SecurityConfig.passwordEncoder();

    String raw = "myPassword";
    String hashed = encoder.encode(raw);

    assertNotEquals(raw, hashed);
    assertTrue(encoder.matches(raw, hashed));
  }
}
