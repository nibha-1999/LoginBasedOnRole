package com.jwt.implementation.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.jwt.implementation.model.Role;
import com.jwt.implementation.model.User;
import com.jwt.implementation.model.UserDTO;
import com.jwt.implementation.repository.RoleRepository;
import com.jwt.implementation.repository.UserRepository;

class DefaultUserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private DefaultUserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testLoadUserByUsername_Success() {
        User user = new User();
        user.setUserName("testUser");
        user.setPassword("hashedPassword");
        Role role = new Role("ROLE_USER");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRole(role);

        when(userRepository.findByUserName("testUser")).thenReturn(user);

        UserDetails userDetails = userService.loadUserByUsername("testUser");

        assertNotNull(userDetails);
        assertEquals("testUser", userDetails.getUsername());
        assertEquals("hashedPassword", userDetails.getPassword());
        assertEquals(1, userDetails.getAuthorities().size());
        assertTrue(userDetails.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")));
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        when(userRepository.findByUserName("nonExistingUser")).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("nonExistingUser"));
    }

    @Test
    void testSave_Success() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName("testUser");
        userDTO.setPassword("testPassword");
        userDTO.setEmail("test@example.com");
        userDTO.setRole("USER");

        Role role = new Role("ROLE_USER");
        when(roleRepository.findByRole("ROLE_USER")).thenReturn(role);

        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User savedUser = userService.save(userDTO);

        assertNotNull(savedUser);
        assertEquals("testUser", savedUser.getUserName());
        assertEquals("test@example.com", savedUser.getEmail());
        assertEquals(1, savedUser.getRole().size());
        assertTrue(savedUser.getRole().contains(role));
    }

    // Add more test cases for edge cases, exceptions, and other scenarios
}

