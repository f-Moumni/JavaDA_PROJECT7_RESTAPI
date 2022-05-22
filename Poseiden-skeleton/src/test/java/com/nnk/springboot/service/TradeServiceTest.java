package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.repositories.TradeRepository;
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
public class TradeServiceTest {

    @Mock
    private static TradeRepository tradeRepository;
    @InjectMocks
    private TradeServiceImpl tradeService;
    private Trade trade;

    @BeforeEach
    void setUp() {
        trade = new Trade("Trade Account", "Type", 12);
    }

    @Test
    void SaveTradeTest_shouldReturnSavedTrade() {
        //ARRANGE
        when(tradeRepository.save(any(Trade.class))).thenReturn(trade);
        //ACT
        Trade result = tradeService.save(trade);
        //ASSERT
        assertThat(result).isEqualToComparingFieldByField(trade);
        verify(tradeRepository).save(trade);
    }

    @Test
    public void findByIdTradeTest_ShouldReturnTrade() throws DataNotFoundException {
        //ARRANGE
        when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));
        // ACT
        Trade result = tradeService.findById(1);
        //ASSERT
        assertThat(result).isEqualToComparingFieldByField(trade);
    }

    @Test
    public void findByIdTradeTest_ShouldThrowException() {
        //ARRANGE
        when(tradeRepository.findById(1)).thenReturn(Optional.empty());
        // ACT //ASSERT
        assertThrows(DataNotFoundException.class, () -> tradeService.findById(1));
    }

    @Test
    public void findAllTradeTest_ShouldReturnAllTrades() {
        //ARRANGE
        when(tradeRepository.findAll()).thenReturn(Arrays.asList(trade));
        // ACT
        List<Trade> listResult = tradeService.findAll();
        //ASSERT
        assertThat(listResult.size()).isEqualTo(1);
    }

    @Test
    public void deleteTradeTest_shouldDeleteTrade() {
        // ARRANGE
        doNothing().when(tradeRepository).delete(trade);
        // ACT
        tradeService.delete(trade);
        //ASSERT
        verify(tradeRepository).delete(trade);
    }
}
