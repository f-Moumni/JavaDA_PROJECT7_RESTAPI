package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private static UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User("admin","$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa","admin name","ADMIN");
    }

    @Test
    void SaveUserTest_shouldReturnSavedUser() {
        //ARRANGE
        when(userRepository.save(any(User.class))).thenReturn(user);
        //ACT
        userService.save(user);
        //ASSERT
        verify(userRepository).save(user);
    }

    @Test
    public void findByIdUserTest_ShouldReturnUser() {
        //ARRANGE
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        // ACT
        User result = userService.findById(1);
        //ASSERT
        assertThat(result).isEqualToComparingFieldByField(user);
    }

    @Test
    public void findByIdUserTest_ShouldThrowException() {
        //ARRANGE
        when(userRepository.findById(1)).thenReturn(Optional.empty());
        // ACT //ASSERT
        assertThrows(IllegalArgumentException.class, () -> userService.findById(1));
    }

    @Test
    public void findAllUserTest_ShouldReturnAllUsers() {
        //ARRANGE
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        // ACT
        List<User> listResult = userService.findAll();
        //ASSERT
        assertThat(listResult.size()).isEqualTo(1);
    }

    @Test
    public void deleteUserTest_shouldDeleteUser() {
        // ARRANGE
        doNothing().when(userRepository).delete(user);
        // ACT
        userService.delete(user);
        //ASSERT
        verify(userRepository).delete(user);
    }
}
