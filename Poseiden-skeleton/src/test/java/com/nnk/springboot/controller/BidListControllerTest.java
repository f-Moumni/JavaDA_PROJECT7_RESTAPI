package com.nnk.springboot.controller;

import com.nnk.springboot.config.OAuth2.CustomOAuth2UserService;
import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.service.BidService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BidListController.class)
public class BidListControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    BidService bidService;
    @MockBean
    UserDetailService userDetailService;

    @MockBean
    CustomOAuth2UserService oAuth2UserService;
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
        //ARRANGE
        when(bidService.findAll()).thenReturn(Arrays.asList(bid));
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
        //ARRANGE
        when(bidService.save(bid)).thenReturn(bid);
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
    void validateBidList_shouldAddBidList_AndReturnTOBidListView() throws Exception {
        //ARRANGE

        when(bidService.save(bid)).thenReturn(bid);
        //ACT
        mvc.perform(post("/bidList/validate")
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
    void UpdateBidList_shouldUpdateBidList_AndReturnTOBidListView() throws Exception {
        //ARRANGE
        when(bidService.save(bid)).thenReturn(bid);
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
        //ARRANGE
        when(bidService.save(bid)).thenReturn(bid);
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
        //ARRANGE
        when(bidService.findById(1)).thenReturn(bid);
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
        //ARRANGE
        when(bidService.findById(1)).thenReturn(bid);
        doNothing().when(bidService).delete(bid);
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
