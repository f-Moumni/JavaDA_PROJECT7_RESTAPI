package com.nnk.springboot.integration.contoller;


import com.nnk.springboot.domain.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import static org.hamcrest.Matchers.containsString;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
@Sql(scripts = "classpath:test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class UserControllerIT {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private WebApplicationContext context;

    private User newUser;

    @BeforeEach
    void setup() {
        newUser = new User("admin", "$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa", "admin name", "ADMIN");
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void getUserTest_shouldReturnUsersView() throws Exception {
        //ACT
        mvc.perform(get("/user/list").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                //ASSERT
                .andDo(print()).andExpect(status().isOk()).andExpect(model().attributeExists("users")).andExpect(view().name("user/list"));
    }

    @Test
    void addUserFormTest_shouldReturnAddUserView() throws Exception {
        //ACT
        mvc.perform(get("/user/add").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                //ASSERT
                .andDo(print()).andExpect(status().isOk()).andExpect(view().name("user/add"));
    }

    @Test
    void validateUser_withInvalidUser_shouldReturnErrorViewAddUser() throws Exception {
        //ARRANGE
        //ACT
        mvc.perform(post("/user/validate").sessionAttr("newUser", newUser).param("username", newUser.getUsername()).param("password", newUser.getPassword()).param("fullname", newUser.getFullname()).param("role", "").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                //ASSERT
                .andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("Role is mandatory"))).andExpect(view().name("user/add"));
    }

    @Test
    void UpdateUser_shouldUpdateUser_AndReturnTOUserView() throws Exception {
        //ACT
        mvc.perform(post("/user/update/1").sessionAttr("user", newUser).param("username", newUser.getUsername()).param("password", newUser.getPassword()).param("fullname", newUser.getFullname()).param("role", newUser.getRole()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                //ASSERT
                .andDo(print()).andExpect(status().isFound()).andExpect(view().name("redirect:/user/list"));

    }

    @Test
    void updateUser_withInvalidUser_shouldReturnErrorInViewUser() throws Exception {

        //ACT
        mvc.perform(post("/user/update/1").sessionAttr("user", newUser).param("username", newUser.getUsername()).param("password", "password").param("fullname", newUser.getFullname()).param("role", newUser.getRole()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                //ASSERT
                .andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("the password should contain at least 8 characters, 1 uppercase, 1 number and 1 special character"))).andExpect(view().name("user/update"));
    }

    @Test
    void showUpdateFormUserTest_shouldReturnUpdateUserView() throws Exception {
        //ACT
        mvc.perform(get("/user/update/1").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                //ASSERT
                .andDo(print()).andExpect(status().isOk()).andExpect(model().attributeExists("user")).andExpect(view().name("user/update"));
    }

    @Test
    void deleteUserTest_shouldReturnToUserView() throws Exception {

        //ACT
        mvc.perform(get("/user/delete/1").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                //ASSERT
                .andDo(print()).andExpect(status().isFound()).andExpect(view().name("redirect:/user/list"));
    }
}
