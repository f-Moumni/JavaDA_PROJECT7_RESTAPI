package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserDetailServiceTest {

    @Mock
    private static UserRepository userRepository;

    @InjectMocks
    private UserDetailService userDetailService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User("admin","$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa","admin name","ADMIN");
    }

    @Test

    void loadUserByUsername_Test_shouldReturnUserDetail()  {
        //ARRANGE
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(user));
        //ACT
        UserDetails userDetail =  userDetailService.loadUserByUsername("admin");
        //ASSERT
        assertThat(userDetail.getUsername()).isEqualTo("admin");
        assertThat(userDetail.getPassword()).isEqualTo("$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa");
    }
    @Test

    void loadUserByUsername_Test_shouldThrowUsernameNotFoundException()  {
        //ARRANGE
        when(userRepository.findByUsername("admin")).thenReturn(Optional.empty());
        //ACT
        assertThrows(UsernameNotFoundException.class,()-> userDetailService.loadUserByUsername("admin"));

    }
}
