package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class RatingServiceTest {

    @Mock
    private static RatingRepository ratingRepository;
    @InjectMocks
    private RatingServiceImpl ratingService;
    private Rating rating;

    @BeforeEach
    void setUp() {
        rating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);
    }

    @Test
    void SaveRatingTest_shouldReturnSavedRating() {
        //ARRANGE
        when(ratingRepository.save(any(Rating.class))).thenReturn(rating);
        //ACT
        Rating result =ratingService.save(rating);
        //ASSERT
        assertThat(result).isEqualToComparingFieldByField(rating);
        verify(ratingRepository).save(rating);
    }

    @Test
    public void findByIdRatingTest_ShouldReturnRating() {
        //ARRANGE
        when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));
        // ACT
        Rating result = ratingService.findById(1);
        //ASSERT
        assertThat(result).isEqualToComparingFieldByField(rating);
    }

    @Test
    public void findByIdRatingTest_ShouldThrowException() {
        //ARRANGE
        when(ratingRepository.findById(1)).thenReturn(Optional.empty());
        // ACT //ASSERT
        assertThrows(IllegalArgumentException.class, () -> ratingService.findById(1));
    }

    @Test
    public void findAllRatingTest_ShouldReturnAllRatings() {
        //ARRANGE
        when(ratingRepository.findAll()).thenReturn(Arrays.asList(rating));
        // ACT
        List<Rating> listResult = ratingService.findAll();
        //ASSERT
        assertThat(listResult.size()).isEqualTo(1);
    }

    @Test
    public void deleteRatingTest_shouldDeleteRating() {
        // ARRANGE
        doNothing().when(ratingRepository).delete(rating);
        // ACT
        ratingService.delete(rating);
        //ASSERT
        verify(ratingRepository).delete(rating);
    }
}
