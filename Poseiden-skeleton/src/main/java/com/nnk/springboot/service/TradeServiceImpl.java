package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.repositories.TradeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TradeServiceImpl implements TradeService {

    /**
     * SLF4J/LOG4J LOGGER instance.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(TradeServiceImpl.class);


    private final TradeRepository tradeRepository;

    @Autowired
    public TradeServiceImpl(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    /**
     * * {@inheritDoc}
     */
    @Override
    public List<Trade> findAll() {
        LOGGER.debug("getting all Trades");
        return tradeRepository.findAll();
    }

    /**
     * * {@inheritDoc}
     */
    @Override
    public Trade save(Trade trade) {
        LOGGER.debug("saving trade {}", trade.getTradeId());
        return tradeRepository.save(trade);
    }
    /**
     * * {@inheritDoc}
     */
    @Override
    public Trade findById(Integer id) throws DataNotFoundException {
        LOGGER.debug("fetching trade by id:{}", id);
        return tradeRepository.findById(id).orElseThrow(() -> {
            LOGGER.error("Invalid trade Id: {} ", id);
            return new DataNotFoundException("Invalid trade Id:" + id);
        });
    }
    /**
     * * {@inheritDoc}
     */
    @Override
    public void delete(Trade trade) {
        LOGGER.debug("deleting trade:{}", trade.getTradeId());
        tradeRepository.delete(trade);
    }
}
