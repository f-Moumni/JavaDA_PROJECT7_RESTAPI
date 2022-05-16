package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TradeService {
    List<Trade> findAll();

    Trade save(Trade trade);

    Trade findById(Integer id);

    void delete(Trade trade);
}
