package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BidServiceImplTest {

    @Mock
    private BidListRepository bidListRepository;
@InjectMocks
    private BidServiceImpl bidService;

    @Before
    public void setUp() throws Exception {
     //   bidService = new BidServiceImpl(bidListRepository);
    }

    @Test
    public void bid_Success_Test() {
        BidList bid = new BidList("Account Test", "Type Test", 10d);
// Save
        //given
        when(bidListRepository.save(any(BidList.class))).thenReturn(bid);
        //when
        bid = bidService.save(bid);
        //then
        Assert.assertNotNull(bid.getBidListId());
        Assert.assertEquals(bid.getBidQuantity(), 10d, 10d);
    }
}
