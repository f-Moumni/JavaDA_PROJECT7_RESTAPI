package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.repositories.TradeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TradeServiceImpl implements TradeService{

    /**
     * SLF4J/LOG4J LOGGER instance.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(TradeServiceImpl.class);

    @Autowired
    private TradeRepository tradeRepository;

    @Override
    public Object findAll() {
        LOGGER.debug("getting all Trades");
        return tradeRepository.findAll();
    }

    @Override
    public Trade save(Trade trade) {
        LOGGER.debug("saving trade {}",trade.getTradeId());
        return tradeRepository.save(trade);
    }

    @Override
    public Trade findById(Integer id) {
        LOGGER.debug("fetching trade by id:{}",id);
        return   tradeRepository.findById(id).orElseThrow( ()->{
            LOGGER.error("Invalid trade Id: {} ", id);
            return new IllegalArgumentException("Invalid trade Id:" + id);
        }   );
    }

    @Override
    public void delete(Trade trade) {
        LOGGER.debug("deleting trade:{}",trade.getTradeId());
        tradeRepository.delete(trade);
    }
}
