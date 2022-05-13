package com.nnk.springboot.controller;

import com.nnk.springboot.config.OAuth2.CustomOAuth2UserService;
import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.controllers.CurveController;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.service.BidService;
import com.nnk.springboot.service.CurvePointServiceTest;
import com.nnk.springboot.service.CurveService;
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
@WebMvcTest(CurveController.class)
public class CurveControllerTest {


    @Autowired
    private MockMvc mvc;
    @MockBean
    CurveService curveService;
    @MockBean
    UserDetailService userDetailService;

    @MockBean
    CustomOAuth2UserService oAuth2UserService;
    @Autowired
    private WebApplicationContext context;
    private CurvePoint curvePoint;

    @BeforeEach
    void setup() {
        curvePoint = new CurvePoint(10, 10d, 30d);
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void getCurvePointTest_shouldReturnCurvePointView() throws Exception {
        //ARRANGE
        when(curveService.findAll()).thenReturn(Arrays.asList(curvePoint));
        //ACT
        mvc.perform(get("/curvePoint/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //ASSERT
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("curvePoints"))
                .andExpect(view().name("curvePoint/list"));
    }

    @Test
    void addBidFormTest_shouldReturnAddCurvePointView() throws Exception {
        //ACT
        mvc.perform(get("/curvePoint/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //ASSERT
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));
    }

    @Test
    void validateCurvePoint_withInvalidCurvePoint_shouldReturnErrorViewAddCurvePoint() throws Exception {
        //ARRANGE
        doNothing().when(curveService).save(curvePoint);
        //ACT
        mvc.perform(post("/curvePoint/validate")
                        .sessionAttr("curvePoint", curvePoint)
                        .param("curveId", String.valueOf(curvePoint.getCurveId()))
                        .param("term",String.valueOf( curvePoint.getTerm()))
                        .param("value", "0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //ASSERT
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "must be greater than or equal to 1")))
                .andExpect(view().name("curvePoint/add"));
    }

    @Test
    void validateCurvePoint_shouldAddCurvePoint_AndReturnTOCurvePointView() throws Exception {
        //ARRANGE
        doNothing().when(curveService).save(curvePoint);
        //ACT
        mvc.perform(post("/curvePoint/validate")
                        .sessionAttr("curvePoint", curvePoint)
                        .param("curveId", String.valueOf(curvePoint.getCurveId()))
                        .param("term",String.valueOf( curvePoint.getTerm()))
                        .param("value", String.valueOf(curvePoint.getValue()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //ASSERT
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/curvePoint/list"));

    }

    @Test
    void UpdateCurvePoint_shouldUpdateCurvePoint_AndReturnTOCurvePointView() throws Exception {
        //ARRANGE
        doNothing().when(curveService).save(curvePoint);
        //ACT
        mvc.perform(post("/curvePoint/update/1")
                        .sessionAttr("curvePoint", curvePoint)
                        .param("curveId", String.valueOf(curvePoint.getCurveId()))
                        .param("term",String.valueOf( curvePoint.getTerm()))
                        .param("value", String.valueOf(curvePoint.getValue()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //ASSERT
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/curvePoint/list"));

    }

    @Test
    void updateCurvePoint_withInvalidCurvePoint_shouldReturnErrorInViewCurvePoint() throws Exception {
        //ARRANGE
        doNothing().when(curveService).save(curvePoint);
        //ACT
        mvc.perform(post("/curvePoint/update/1")
                        .sessionAttr("curvePoint", curvePoint)
                        .param("curveId", String.valueOf(curvePoint.getCurveId()))
                        .param("term",String.valueOf( curvePoint.getTerm()))
                        .param("value", "0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //ASSERT
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "must be greater than or equal to 1")))
                .andExpect(view().name("curvePoint/update"));
    }

    @Test
    void showUpdateFormCurvePointTest_shouldReturnUpdateCurvePointView() throws Exception {
        //ARRANGE
        when(curveService.findById(1)).thenReturn(curvePoint);
        //ACT
        mvc.perform(get("/curvePoint/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //ASSERT
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("curvePoint"))
                .andExpect(view().name("curvePoint/update"));
    }

    @Test
    void deleteCurvePointTest_shouldReturnToCurvePointView() throws Exception {
        //ARRANGE
        when(curveService.findById(1)).thenReturn(curvePoint);
        doNothing().when(curveService).delete(curvePoint);
        //ACT
        mvc.perform(get("/curvePoint/delete/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //ASSERT
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/curvePoint/list"));
    }
}
