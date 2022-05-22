package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.exception.DataNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TradeService {

    /**
     * get all trades
     * @return
     */
    List<Trade> findAll();

    /**
     * save a given trade
     * @param trade
     * @return
     */
    Trade save(Trade trade);

    /**
     * find trade by id
     * @param id
     * @return
     */
    Trade findById(Integer id) throws DataNotFoundException;

    /**
     * delete given trade
     * @param trade
     */
    void delete(Trade trade);
}
