package com.nnk.springboot.integration.contoller;

import com.nnk.springboot.domain.BidList;

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
public class BidListControllerIT {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private WebApplicationContext context;
    private BidList bid;

    @BeforeEach
    void setup() {
        bid = new BidList("Account Test", "TypeTest", 10d);
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void getBidListTest_shouldReturnBidListView() throws Exception {

        //ACT
        mvc.perform(get("/bidList/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //ASSERT
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("bids"))
                .andExpect(view().name("bidList/list"));
    }

    @Test
    void addBidFormTest_shouldReturnAddBidListView() throws Exception {
        //ACT
        mvc.perform(get("/bidList/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //ASSERT
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"));
    }

    @Test
    void validateBidList_withInvalidBidList_shouldViewAddBidList() throws Exception {

        //ACT
        mvc.perform(post("/bidList/validate")
                        .sessionAttr("bidList", bid)
                        .param("account", bid.getAccount())
                        .param("type", bid.getType())
                        .param("bidQuantity", "0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //ASSERT
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "must be greater than or equal to 1")))
                .andExpect(view().name("bidList/add"));
    }

    @Test
    void UpdateBidList_shouldUpdateBidList_AndReturnTOBidListView() throws Exception {

        //ACT
        mvc.perform(post("/bidList/update/1")
                        .sessionAttr("bidList", bid)
                        .param("account", bid.getAccount())
                        .param("type", bid.getType())
                        .param("bidQuantity", String.valueOf(bid.getBidQuantity()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //ASSERT
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/bidList/list"));

    }

    @Test
    void updateBidList_withInvalidBidList_shouldReturnErrorInViewBidList() throws Exception {

        //ACT
        mvc.perform(post("/bidList/update/1")
                        .sessionAttr("bidList", bid)
                        .param("account", bid.getAccount())
                        .param("type", bid.getType())
                        .param("bidQuantity", "0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //ASSERT
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "must be greater than or equal to 1")))
                .andExpect(view().name("bidList/update"));
    }

    @Test
    void showUpdateFormBidListTest_shouldReturnUpdateBidListView() throws Exception {

        //ACT
        mvc.perform(get("/bidList/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //ASSERT
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("bidList"))
                .andExpect(view().name("bidList/update"));
    }

    @Test
    void deleteBidListTest_shouldReturnToBidListView() throws Exception {

        //ACT
        mvc.perform(get("/bidList/delete/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //ASSERT
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/bidList/list"));
    }
}
