package com.jwt.implementation.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.jwt.implementation.model.User;
import com.jwt.implementation.model.UserDTO;
import com.jwt.implementation.repository.UserRepository;
import com.jwt.implementation.service.DefaultUserService;

class RestAppControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private DefaultUserService userService;

    @InjectMocks
    private RestAppController restAppController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testRegisterUser_Success() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName("testUser");
        userDTO.setPassword("testPassword");
        userDTO.setEmail("test@example.com");
        userDTO.setRole("USER");

        when(userService.save(userDTO)).thenReturn(new User()); // Mocking service method

        ResponseEntity<Object> response = restAppController.registerUser(userDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    // Additional test cases can be added
}
