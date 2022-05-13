package com.nnk.springboot.controller;

import com.nnk.springboot.config.OAuth2.CustomOAuth2UserService;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.UserService;
import com.nnk.springboot.service.UserDetailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private UserService userService;
    @MockBean
    private UserDetailService userDetailService;

    @MockBean
    private CustomOAuth2UserService oAuth2UserService;
    @Autowired
    private WebApplicationContext context;
    private User user;

    @BeforeEach
    void setup() {
        user = new User("admin", "$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa", "admin name", "ADMIN");
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void getUserTest_shouldReturnUserView() throws Exception {
        //ARRANGE
        when(userService.findAll()).thenReturn(Arrays.asList(user));
        //ACT
        mvc.perform(get("/user/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //ASSERT
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("users"))
                .andExpect(view().name("user/list"));
    }

    @Test
    void addUserFormTest_shouldReturnAddUserView() throws Exception {
        //ACT
        mvc.perform(get("/user/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //ASSERT
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"));
    }

    @Test
    void validateUser_withInvalidUser_shouldReturnErrorViewAddUser() throws Exception {
        //ARRANGE
        doNothing().when(userService).save(user);
        //ACT
        mvc.perform(post("/user/validate")
                        .sessionAttr("user", user)
                        .param("username", user.getUsername())
                        .param("password", user.getPassword())
                        .param("fullname", user.getFullname())
                        .param("role", "")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //ASSERT
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "Role is mandatory")))
                .andExpect(view().name("user/add"));
    }

    @Test
    void validateUser_shouldAddUser_AndReturnTOUserView() throws Exception {
        //ARRANGE
        doNothing().when(userService).save(user);
        //ACT
        mvc.perform(post("/user/validate")
                        .sessionAttr("user", user)
                        .param("username", user.getUsername())
                        .param("password", user.getPassword())
                        .param("fullname", user.getFullname())
                        .param("role", user.getRole())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //ASSERT
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/user/list"));

    }

    @Test
    void UpdateUser_shouldUpdateUser_AndReturnTOUserView() throws Exception {
        //ARRANGE
        doNothing().when(userService).save(user);
        //ACT
        mvc.perform(post("/user/update/1")
                        .sessionAttr("user", user)
                        .param("username", user.getUsername())
                        .param("password", user.getPassword())
                        .param("fullname", user.getFullname())
                        .param("role", user.getRole())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //ASSERT
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/user/list"));

    }

    @Test
    void updateUser_withInvalidUser_shouldReturnErrorInViewUser() throws Exception {
        //ARRANGE
        doNothing().when(userService).save(user);
        //ACT
        mvc.perform(post("/user/update/1")
                        .sessionAttr("user", user)
                        .param("username", user.getUsername())
                        .param("password", "password")
                        .param("fullname", user.getFullname())
                        .param("role", user.getRole())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //ASSERT
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "the password should contain at least 8 characters, 1 uppercase, 1 number and 1 special character")))
                .andExpect(view().name("user/update"));
    }

    @Test
    void showUpdateFormUserTest_shouldReturnUpdateUserView() throws Exception {
        //ARRANGE
        when(userService.findById(1)).thenReturn(user);
        //ACT
        mvc.perform(get("/user/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //ASSERT
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(view().name("user/update"));
    }

    @Test
    void deleteUserTest_shouldReturnToUserView() throws Exception {
        //ARRANGE
        when(userService.findById(1)).thenReturn(user);
        doNothing().when(userService).delete(user);
        //ACT
        mvc.perform(get("/user/delete/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //ASSERT
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/user/list"));
    }
}
