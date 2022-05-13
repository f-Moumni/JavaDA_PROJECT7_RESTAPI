package com.nnk.springboot.service;


import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
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


@ExtendWith(MockitoExtension.class)
public class BidServiceImplTest {

    @Mock
    private BidListRepository bidListRepository;
    @InjectMocks
    private BidServiceImpl bidService;
    private BidList bid;

    @BeforeEach
    void setUp() {
        bid = new BidList("Account Test", "Type Test", 10d);
    }

    @Test
    void SaveBidTest_shouldReturnSavedBid() {
        //ARRANGE
        when(bidListRepository.save(any(BidList.class))).thenReturn(bid);
        //ACT
        BidList result = bidService.save(bid);
        //ASSERT
        assertThat(result.getBidQuantity()).isEqualTo(10d);
    }
    @Test
    public void findByIdBidListTest_ShouldReturnBidList() {
        //ARRANGE
        when(bidListRepository.findById(1)).thenReturn(Optional.of(bid));
        // ACT
        BidList result = bidService.findById(1);
        //ASSERT
        assertThat(result).isEqualToComparingFieldByField(bid);
    }
    @Test
    public void findByIdBidListTest_ShouldThrowException() {
        //ARRANGE
       when(bidListRepository.findById(1)).thenReturn(Optional.empty());
        // ACT //ASSERT
        assertThrows(IllegalArgumentException.class,()-> bidService.findById(1));
    }

    @Test
    public void findAllBidListTest_ShouldReturnAllBidLists() {
        //ARRANGE
        when(bidListRepository.findAll()).thenReturn(Arrays.asList(bid));
        // ACT
        List<BidList> listResult = bidService.findAll();
        //ASSERT
        assertThat(listResult.size()).isEqualTo(1);
    }
    @Test
    public void deleteBidListTest_shouldDeleteBidList() {
        // ARRANGE
      doNothing().when(bidListRepository).delete(bid);
        // ACT
        bidService.delete(bid);
        //ASSERT
     verify(bidListRepository).delete(bid);
    }
}
