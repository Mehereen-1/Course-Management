package com.example.soft_school.service;

import com.example.soft_school.entity.Role;
import com.example.soft_school.entity.User;
import com.example.soft_school.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    void testRegisterUserSuccessfully() {
        // Arrange
        String username = "testuser";
        String email = "test@example.com";
        String password = "password123";
        Role role = Role.STUDENT;
        String encodedPassword = "encoded_password";

        User expectedUser = new User();
        expectedUser.setId(1L);
        expectedUser.setUsername(username);
        expectedUser.setEmail(email);
        expectedUser.setPassword(encodedPassword);
        expectedUser.setRole(role);

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
        when(userRepository.save(any(User.class))).thenReturn(expectedUser);

        // Act
        User result = userService.registerUser(username, email, password, role);

        // Assert
        assertNotNull(result);
        assertEquals(username, result.getUsername());
        assertEquals(email, result.getEmail());
        assertEquals(encodedPassword, result.getPassword());
        assertEquals(role, result.getRole());

        // Verify interactions
        verify(userRepository).findByUsername(username);
        verify(passwordEncoder).encode(password);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testRegisterUserWithDuplicateUsername() {
        // Arrange
        String username = "existinguser";
        User existingUser = new User();
        existingUser.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(existingUser));

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            userService.registerUser(username, "test@example.com", "password123", Role.STUDENT);
        });

        assertEquals("Username already exists", exception.getMessage());

        // Verify that save was never called
        verify(userRepository, never()).save(any(User.class));
        verify(passwordEncoder, never()).encode(anyString());
    }

    @Test
    void testRegisterUserTeacher() {
        // Arrange
        String username = "teacheruser";
        String email = "teacher@example.com";
        String password = "password123";
        Role role = Role.TEACHER;
        String encodedPassword = "encoded_password";

        User expectedUser = new User();
        expectedUser.setId(2L);
        expectedUser.setUsername(username);
        expectedUser.setEmail(email);
        expectedUser.setPassword(encodedPassword);
        expectedUser.setRole(role);

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
        when(userRepository.save(any(User.class))).thenReturn(expectedUser);

        // Act
        User result = userService.registerUser(username, email, password, role);

        // Assert
        assertNotNull(result);
        assertEquals(Role.TEACHER, result.getRole());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testRegisterUserPasswordEncoded() {
        // Arrange
        String plainPassword = "plainpassword";
        String encodedPassword = "encoded_password";

        User savedUser = new User();
        savedUser.setPassword(encodedPassword);

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(plainPassword)).thenReturn(encodedPassword);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Act
        User result = userService.registerUser("user", "email@test.com", plainPassword, Role.STUDENT);

        // Assert
        assertEquals(encodedPassword, result.getPassword());
        assertNotEquals(plainPassword, result.getPassword());
        verify(passwordEncoder).encode(plainPassword);
    }
}
