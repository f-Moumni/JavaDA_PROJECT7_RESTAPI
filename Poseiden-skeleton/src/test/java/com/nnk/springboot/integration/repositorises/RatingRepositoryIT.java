package com.nnk.springboot.integration.repositorises;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Sql(scripts = "classpath:test-data.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class RatingRepositoryIT {

    @Autowired
    private RatingRepository ratingRepository;
    private Rating rating;

    @BeforeEach
    void setUp() {
        rating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);

    }

    @Test
    public void ratingTest_update() {
        //ARRANGE
        rating.setId(1);
        rating.setOrderNumber(20);
        //ACT
        rating = ratingRepository.save(rating);
        //ASSERT
        assertTrue(rating.getOrderNumber() == 20);
    }

    @Test
    public void ratingTest_findAll() {
        //ACT
        List<Rating> listResult = ratingRepository.findAll();
        //ASSERT
        assertThat(listResult.size()).isEqualTo(2);
    }

    @Test
    public void ratingTest_delete() {
        //ARRANGE
        rating.setId(2);
        //ACT
        ratingRepository.delete(rating);
        Optional<Rating> ratingList = ratingRepository.findById(2);
        //ASSERT
        assertFalse(ratingList.isPresent());
    }
}
