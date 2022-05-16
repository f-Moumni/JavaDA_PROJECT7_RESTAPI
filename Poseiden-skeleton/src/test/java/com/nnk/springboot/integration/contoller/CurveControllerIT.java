package com.nnk.springboot.integration.contoller;

import com.nnk.springboot.domain.CurvePoint;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
@Sql(scripts = "classpath:test-data.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class CurveControllerIT {

    @Autowired
    private MockMvc mvc;

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
    void addCurveFormTest_shouldReturnAddCurvePointView() throws Exception {
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

        //ACT
        mvc.perform(post("/curvePoint/validate")
                        .sessionAttr("curvePoint", curvePoint)
                        .param("curveId", String.valueOf(curvePoint.getCurveId()))
                        .param("term", String.valueOf(curvePoint.getTerm()))
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
    void UpdateCurvePoint_shouldUpdateCurvePoint_AndReturnTOCurvePointView() throws Exception {

        //ACT
        mvc.perform(post("/curvePoint/update/1")
                        .sessionAttr("curvePoint", curvePoint)
                        .param("curveId", String.valueOf(curvePoint.getCurveId()))
                        .param("term", String.valueOf(curvePoint.getTerm()))
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

        //ACT
        mvc.perform(post("/curvePoint/update/1")
                        .sessionAttr("curvePoint", curvePoint)
                        .param("curveId", String.valueOf(curvePoint.getCurveId()))
                        .param("term", String.valueOf(curvePoint.getTerm()))
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
