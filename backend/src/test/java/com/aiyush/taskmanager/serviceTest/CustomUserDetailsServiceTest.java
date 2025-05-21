package com.aiyush.taskmanager.serviceTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.aiyush.taskmanager.entity.User;
import com.aiyush.taskmanager.repository.UserRepository;
import com.aiyush.taskmanager.service.CustomUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    private User user;

    @BeforeEach
    public void setup(){
        user = new User();
        user.setId(1L);
        user.setEmail("aiyush.saini@gmail.com");
        user.setUsername("Aiyushsaini");
        user.setPassword("AiyushPassword");
    }

    @Test
    public void testLoadByUsername_UserFound(){
        when(
                userRepository.findByEmail(user.getEmail())
        ).thenReturn(Optional.of(user));

        UserDetails userDetails = customUserDetailsService.loadUserByUsername("aiyush.saini@gmail.com");

        assertNotNull(userDetails);
        assertEquals(user.getUsername(), userDetails.getUsername());
        verify(userRepository,times(1)).findByEmail("aiyush.saini@gmail.com");

    }

    @Test
    public void testLoadByUsername_UserNotFound(){

        when(userRepository.findByEmail("aiyush@gmail.com")).thenReturn(Optional.empty());

        UsernameNotFoundException usernameNotFoundException = assertThrows(UsernameNotFoundException.class,()->customUserDetailsService.loadUserByUsername("aiyush@gmail.com"));

        assertEquals("User not found with email: aiyush@gmail.com",usernameNotFoundException.getMessage());

        verify(userRepository,times(1)).findByEmail("aiyush@gmail.com");

    }


}
