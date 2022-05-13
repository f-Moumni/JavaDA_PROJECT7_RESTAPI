package com.nnk.springboot.integration.repositorises;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Sql(scripts = "classpath:test-data.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class TradeRepositoryIT {

    @Autowired
    private TradeRepository tradeRepository;
    private Trade trade;

    @BeforeEach
    void setUp() {
        trade = new Trade("Trade Account", "Type", 12);
    }

    @Test
    public void tradeTest_Update() {
        trade.setTradeId(1);
        trade.setAccount("Trade Account Update");
        trade = tradeRepository.save(trade);
        assertTrue(trade.getAccount().equals("Trade Account Update"));
    }

    @Test
    public void tradeTest_FindAll() {

        List<Trade> listResult = tradeRepository.findAll();
        assertTrue(listResult.size() > 0);
    }

    @Test
    public void tradeTest_Delete() {
        trade.setTradeId(1);
        tradeRepository.delete(trade);
        Optional<Trade> tradeList = tradeRepository.findById(1);
        assertFalse(tradeList.isPresent());
    }
}
