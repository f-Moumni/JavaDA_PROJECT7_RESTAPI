package com.nnk.springboot.integration.contoller;

import com.nnk.springboot.domain.RuleName;
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
public class RuleNameControllerIT {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private WebApplicationContext context;
    private RuleName ruleName;

    @BeforeEach
    void setup() {
        ruleName = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void getRuleNameTest_shouldReturnRuleNameView() throws Exception {
        //ACT
        mvc.perform(get("/ruleName/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //ASSERT
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("rules"))
                .andExpect(view().name("ruleName/list"));
    }

    @Test
    void addRuleNameFormTest_shouldReturnAddRuleNameView() throws Exception {
        //ACT
        mvc.perform(get("/ruleName/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //ASSERT
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));
    }

    @Test
    void validateRuleName_withInvalidRuleName_shouldReturnErrorViewAddRuleName() throws Exception {
        //ACT
        mvc.perform(post("/ruleName/validate")
                        .sessionAttr("ruleName", ruleName)
                        .param("name", ruleName.getName())
                        .param("description", ruleName.getDescription())
                        .param("json", ruleName.getJson())
                        .param("template", ruleName.getTemplate())
                        .param("sqlStr", "")
                        .param("sqlPart", ruleName.getSqlStr())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //ASSERT
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "sqlStr required")))
                .andExpect(view().name("ruleName/add"));
    }

    @Test
    void UpdateRuleName_shouldUpdateRuleName_AndReturnTORuleNameView() throws Exception {

        //ACT
        mvc.perform(post("/ruleName/update/1")
                        .sessionAttr("ruleName", ruleName)
                        .param("name", ruleName.getName())
                        .param("description", ruleName.getDescription())
                        .param("json", ruleName.getJson())
                        .param("template", ruleName.getTemplate())
                        .param("sqlStr", ruleName.getSqlStr())
                        .param("sqlPart", ruleName.getSqlStr())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //ASSERT
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/ruleName/list"));

    }

    @Test
    void updateRuleName_withInvalidRuleName_shouldReturnErrorInViewRuleName() throws Exception {

        //ACT
        mvc.perform(post("/ruleName/update/2")
                        .sessionAttr("ruleName", ruleName)
                        .param("name", ruleName.getName())
                        .param("description", ruleName.getDescription())
                        .param("json", ruleName.getJson())
                        .param("template", ruleName.getTemplate())
                        .param("sqlStr", "")
                        .param("sqlPart", ruleName.getSqlStr())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //ASSERT
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "sqlStr required")))
                .andExpect(view().name("ruleName/update"));
    }

    @Test
    void showUpdateFormRuleNameTest_shouldReturnUpdateRuleNameView() throws Exception {

        mvc.perform(get("/ruleName/update/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //ASSERT
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ruleName"))
                .andExpect(view().name("ruleName/update"));
    }

    @Test
    void deleteRuleNameTest_shouldReturnToRuleNameView() throws Exception {
        //ACT
        mvc.perform(get("/ruleName/delete/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //ASSERT
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/ruleName/list"));
    }
}
