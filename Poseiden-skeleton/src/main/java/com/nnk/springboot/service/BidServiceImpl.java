package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BidServiceImpl implements BidService {
    /**
     * SLF4J/LOG4J LOGGER instance.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(BidServiceImpl.class);

    private final BidListRepository bidListRepository;

    @Autowired
    public BidServiceImpl(BidListRepository bidListRepository) {
        this.bidListRepository = bidListRepository;
    }


    @Override
    public BidList findById(Integer id) {
        LOGGER.debug("fetching bid by id:{}", id);
        return bidListRepository.findById(id).orElseThrow(() -> {
            LOGGER.error("Invalid bid Id: {} ", id);
            return new IllegalArgumentException("Invalid bid Id:" + id);
        });
    }

    @Override
    public List<BidList> findAll() {
        LOGGER.debug("getting all bids");
        return bidListRepository.findAll();
    }

    @Override
    public BidList save(BidList bid) {
        LOGGER.debug("saving bid {}", bid.getAccount());
        return bidListRepository.save(bid);
    }

    @Override
    public void delete(BidList bidList) {
        LOGGER.debug("deleting bid:{}", bidList.getBidListId());
        bidListRepository.delete(bidList);
    }
}
