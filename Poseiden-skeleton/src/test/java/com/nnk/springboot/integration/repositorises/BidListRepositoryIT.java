package com.nnk.springboot.integration.repositorises;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Sql(scripts = "classpath:test-data.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class BidListRepositoryIT{

    @Autowired
    private BidListRepository bidListRepository;
    private BidList bid;

    @BeforeEach
    void setUp() {
        bid = new BidList("Account Test", "Type Test", 10d);
    }


    @Test
    public void bidListTest_update() {
		// ARRANGE
	bid.setBidListId(2);
		bid.setBidQuantity(20d);
		//ACT
		bid = bidListRepository.save(bid);
		//ASSERT
		assertThat(bid.getBidQuantity()).isEqualTo(20d);
	}
	@Test
	public void bidListTest_findAll() {
		// ACT
		List<BidList> listResult = bidListRepository.findAll();
		//ASSERT
		assertThat(listResult.size()).isEqualTo(2);
	}
	@Test
	public void bidListTest_delete() {
        // ARRANGE
        bid.setBidListId(2);
		// ACT
        bidListRepository.delete(bid);
		//ASSERT
        Optional<BidList> bidList = bidListRepository.findById(2);
        assertFalse(bidList.isPresent());
    }
}
