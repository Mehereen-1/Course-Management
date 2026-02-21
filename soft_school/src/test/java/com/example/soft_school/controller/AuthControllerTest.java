package com.example.soft_school.controller;

import com.example.soft_school.entity.Role;
import com.example.soft_school.entity.User;
import com.example.soft_school.repository.UserRepository;
import com.example.soft_school.security.JwtService;
import com.example.soft_school.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private AuthController authController;
    private UserDetails testUserDetails;
    private User testUser;

    @BeforeEach
    void setUp() {
        authController = new AuthController(
                authenticationManager,
                userDetailsService,
                jwtService,
                userService,
                userRepository
        );

        testUserDetails = org.springframework.security.core.userdetails.User.builder()
                .username("testuser")
                .password("password123")
                .roles("STUDENT")
                .build();

        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setEmail("test@example.com");
        testUser.setPassword("encoded_password");
        testUser.setRole(Role.STUDENT);
    }

    @Test
    void testLoginSuccessfully() {
        // Arrange
        String username = "testuser";
        String password = "password123";
        String token = "jwt_token_value";

        AuthController.LoginRequest loginRequest = new AuthController.LoginRequest(username, password);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(new UsernamePasswordAuthenticationToken(username, password));
        when(userDetailsService.loadUserByUsername(username)).thenReturn(testUserDetails);
        when(jwtService.generateToken(testUserDetails)).thenReturn(token);

        // Act
        var response = authController.login(loginRequest);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(token, response.getBody().token());
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testLoginWithInvalidCredentials() {
        // Arrange
        String username = "testuser";
        String password = "wrongpassword";

        AuthController.LoginRequest loginRequest = new AuthController.LoginRequest(username, password);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        // Act & Assert
        assertThrows(BadCredentialsException.class, () -> {
            authController.login(loginRequest);
        });
    }

    @Test
    void testRegisterUserSuccessfully() {
        // Arrange
        AuthController.RegisterRequest registerRequest = new AuthController.RegisterRequest(
                "newuser",
                "newuser@example.com",
                "password123",
                Role.STUDENT
        );

        User newUser = new User();
        newUser.setId(2L);
        newUser.setUsername("newuser");
        newUser.setEmail("newuser@example.com");
        newUser.setRole(Role.STUDENT);

        when(userService.registerUser("newuser", "newuser@example.com", "password123", Role.STUDENT))
                .thenReturn(newUser);

        // Act
        var response = authController.register(registerRequest);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals("newuser", response.getBody().getUsername());
        assertEquals("newuser@example.com", response.getBody().getEmail());
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testRegisterAdminUserFails() {
        // Arrange
        AuthController.RegisterRequest registerRequest = new AuthController.RegisterRequest(
                "adminuser",
                "admin@example.com",
                "password123",
                Role.ADMIN
        );

        // Act
        var response = authController.register(registerRequest);

        // Assert
        assertNotNull(response);
        assertEquals(400, response.getStatusCode().value());
        verify(userService, never()).registerUser(anyString(), anyString(), anyString(), any(Role.class));
    }

    @Test
    void testRegisterTeacherSuccessfully() {
        // Arrange
        AuthController.RegisterRequest registerRequest = new AuthController.RegisterRequest(
                "teacheruser",
                "teacher@example.com",
                "password123",
                Role.TEACHER
        );

        User teacherUser = new User();
        teacherUser.setId(3L);
        teacherUser.setUsername("teacheruser");
        teacherUser.setEmail("teacher@example.com");
        teacherUser.setRole(Role.TEACHER);

        when(userService.registerUser("teacheruser", "teacher@example.com", "password123", Role.TEACHER))
                .thenReturn(teacherUser);

        // Act
        var response = authController.register(registerRequest);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(Role.TEACHER, response.getBody().getRole());
    }

    @Test
    void testListUsersSuccessfully() {
        // Arrange
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("user1");
        user1.setEmail("user1@example.com");
        user1.setRole(Role.STUDENT);

        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("user2");
        user2.setEmail("user2@example.com");
        user2.setRole(Role.TEACHER);

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        when(userRepository.findAll()).thenReturn(users);

        // Act
        var response = authController.listUsers();

        // Assert
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("user1", response.getBody().get(0).getUsername());
        assertEquals("user2", response.getBody().get(1).getUsername());
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testListUsersEmpty() {
        // Arrange
        when(userRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        var response = authController.listUsers();

        // Assert
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().size());
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void testLoginWithNullUsername() {
        // Arrange
        AuthController.LoginRequest loginRequest = new AuthController.LoginRequest(null, "password123");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new IllegalArgumentException("Username cannot be null"));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            authController.login(loginRequest);
        });
    }
}

