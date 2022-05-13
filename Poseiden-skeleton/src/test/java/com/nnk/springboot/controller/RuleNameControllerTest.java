package com.nnk.springboot.controller;

import com.nnk.springboot.config.OAuth2.CustomOAuth2UserService;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.service.RuleService;
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
@WebMvcTest(RuleNameController.class)
public class RuleNameControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private RuleService ruleNameService;
    @MockBean
    private UserDetailService userDetailService;
    @MockBean
    private CustomOAuth2UserService oAuth2UserService;
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
        //ARRANGE
        when(ruleNameService.findAll()).thenReturn(Arrays.asList(ruleName));
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
        //ARRANGE
        when(ruleNameService.save(ruleName)).thenReturn(ruleName);
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
    void validateRuleName_shouldAddRuleName_AndReturnToRuleNameView() throws Exception {
        //ARRANGE
        when(ruleNameService.save(ruleName)).thenReturn(ruleName);
        //ACT
        mvc.perform(post("/ruleName/validate")
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
    void UpdateRuleName_shouldUpdateRuleName_AndReturnTORuleNameView() throws Exception {
        //ARRANGE
        when(ruleNameService.save(ruleName)).thenReturn(ruleName);
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
        //ARRANGE
        when(ruleNameService.save(ruleName)).thenReturn(ruleName);
        //ACT
        mvc.perform(post("/ruleName/update/1")
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
        //ARRANGE
        when(ruleNameService.findById(1)).thenReturn(ruleName);
        //ACT
        mvc.perform(get("/ruleName/update/1")
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
        //ARRANGE
        when(ruleNameService.findById(1)).thenReturn(ruleName);
        doNothing().when(ruleNameService).delete(ruleName);
        //ACT
        mvc.perform(get("/ruleName/delete/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //ASSERT
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/ruleName/list"));
    }
}
