package com.nnk.springboot.controller;


import com.nnk.springboot.config.OAuth2.CustomOAuth2UserService;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.service.TradeService;
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
@WebMvcTest(TradeController.class)
public class TradeControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private TradeService tradeService;
    @MockBean
    private UserDetailService userDetailService;

    @MockBean
    private CustomOAuth2UserService oAuth2UserService;
    @Autowired
    private WebApplicationContext context;
    private Trade trade;

    @BeforeEach
    void setup() throws Exception {

        trade = new Trade("Account", "Type", 12);
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void getTradeTest_shouldReturnTradeView() throws Exception {
        //ARRANGE
        when(tradeService.findAll()).thenReturn(Arrays.asList(trade));
        //ACT
        mvc.perform(get("/trade/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //ASSERT
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("trades"))
                .andExpect(view().name("trade/list"));
    }

    @Test
    void addTradeFormTest_shouldReturnAddTradeView() throws Exception {
        //ACT
        mvc.perform(get("/trade/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //ASSERT
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"));
    }

    @Test
    void validateTrade_withInvalidTrade_shouldReturnErrorViewAddTrade() throws Exception {
        //ARRANGE
        when(tradeService.save(trade)).thenReturn(trade);
        //ACT
        mvc.perform(post("/trade/validate")
                        .sessionAttr("trade", trade)
                        .param("account", trade.getAccount())
                        .param("type", trade.getType())
                        .param("buyQuantity", "0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //ASSERT
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "must be greater than or equal to 1")))
                .andExpect(view().name("trade/add"));
    }

    @Test
    void validateTrade_shouldAddTrade_AndReturnTOTradeView() throws Exception {
        //ARRANGE
        when(tradeService.save(trade)).thenReturn(trade);
        //ACT
        mvc.perform(post("/trade/validate")
                        .sessionAttr("trade", trade)
                        .param("account", trade.getAccount())
                        .param("type", trade.getType())
                        .param("buyQuantity", String.valueOf(trade.getBuyQuantity()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //ASSERT
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/trade/list"));

    }

    @Test
    void UpdateTrade_shouldUpdateTrade_AndReturnTOTradeView() throws Exception {
        //ARRANGE
        when(tradeService.save(trade)).thenReturn(trade);
        //ACT
        mvc.perform(post("/trade/update/1")
                        .sessionAttr("trade", trade)
                        .param("account", trade.getAccount())
                        .param("type", trade.getType())
                        .param("buyQuantity", String.valueOf(trade.getBuyQuantity()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //ASSERT
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/trade/list"));

    }

    @Test
    void updateTrade_withInvalidTrade_shouldReturnErrorInViewTrade() throws Exception {
        //ARRANGE
        when(tradeService.save(trade)).thenReturn(trade);
        //ACT
        mvc.perform(post("/trade/update/1")
                        .sessionAttr("trade", trade)
                        .param("account", trade.getAccount())
                        .param("type", trade.getType())
                        .param("buyQuantity", "0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //ASSERT
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "must be greater than or equal to 1")))
                .andExpect(view().name("trade/update"));
    }

    @Test
    void showUpdateFormTradeTest_shouldReturnUpdateTradeView() throws Exception {
        //ARRANGE
        when(tradeService.findById(1)).thenReturn(trade);
        //ACT
        mvc.perform(get("/trade/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //ASSERT
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("trade"))
                .andExpect(view().name("trade/update"));
    }

    @Test
    void deleteTradeTest_shouldReturnToTradeView() throws Exception {
        //ARRANGE
        when(tradeService.findById(1)).thenReturn(trade);
        doNothing().when(tradeService).delete(trade);
        //ACT
        mvc.perform(get("/trade/delete/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //ASSERT
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/trade/list"));
    }
}
