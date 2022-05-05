package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import org.springframework.stereotype.Service;

@Service
public interface TradeService {
    Object findAll();


    Trade save(Trade trade);

    Trade findById(Integer id);

    void delete(Trade trade);
}
