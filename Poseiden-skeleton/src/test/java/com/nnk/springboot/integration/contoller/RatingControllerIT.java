package com.nnk.springboot.integration.contoller;

import com.nnk.springboot.domain.Rating;
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
public class RatingControllerIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;
    private Rating rating;

    @BeforeEach
    void setup() {
        rating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void getRatingTest_shouldReturnRatingView() throws Exception {

        //ACT
        mvc.perform(get("/rating/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //ASSERT
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ratings"))
                .andExpect(view().name("rating/list"));
    }

    @Test
    void addRatingFormTest_shouldReturnAddRatingView() throws Exception {
        //ACT
        mvc.perform(get("/rating/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //ASSERT
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));
    }

    @Test
    void validateRating_withInvalidRating_shouldReturnErrorViewAddRating() throws Exception {

        //ACT
        mvc.perform(post("/rating/validate")
                        .sessionAttr("rating", rating)
                        .param("moodysRating", rating.getMoodysRating())
                        .param("sandPRating", rating.getSandPRating())
                        .param("fitchRating", "0")
                        .param("orderNumber", "0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //ASSERT
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "must be greater than or equal to 1")))
                .andExpect(view().name("rating/add"));
    }



    @Test
    void UpdateRating_shouldUpdateRating_AndReturnTORatingView() throws Exception {

        //ACT
        mvc.perform(post("/rating/update/2")
                        .sessionAttr("rating", rating)
                        .param("moodysRating", rating.getMoodysRating())
                        .param("sandPRating", rating.getSandPRating())
                        .param("fitchRating", String.valueOf(rating.getFitchRating()))
                        .param("orderNumber", String.valueOf(rating.getOrderNumber()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //ASSERT
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/rating/list"));

    }

    @Test
    void updateRating_withInvalidRating_shouldReturnErrorInViewRating() throws Exception {

        //ACT
        mvc.perform(post("/rating/update/1")
                        .sessionAttr("rating", rating)
                        .param("moodysRating", rating.getMoodysRating())
                        .param("sandPRating", rating.getSandPRating())
                        .param("fitchRating", "0")
                        .param("orderNumber", "0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //ASSERT
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "must be greater than or equal to 1")))
                .andExpect(view().name("rating/update"));
    }

    @Test
    void showUpdateFormRatingTest_shouldReturnUpdateRatingView() throws Exception {

        //ACT
        mvc.perform(get("/rating/update/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //ASSERT
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("rating"))
                .andExpect(view().name("rating/update"));
    }

    @Test
    void deleteRatingTest_shouldReturnToRatingView() throws Exception {
        //ACT
        mvc.perform(get("/rating/delete/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //ASSERT
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/rating/list"));
    }
}