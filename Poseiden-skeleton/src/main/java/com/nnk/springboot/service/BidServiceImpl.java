package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.repositories.BidListRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
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

    /**
     * * {@inheritDoc}
     */
    @Override
    public BidList findById(Integer id) throws DataNotFoundException {
        LOGGER.debug("fetching bid by id:{}", id);
        return bidListRepository.findById(id).orElseThrow(() -> {
            LOGGER.error("Invalid bid Id: {} ", id);
            return new DataNotFoundException("Invalid bid Id:" + id);
        });
    }
    /**
     * * {@inheritDoc}
     */
    @Override
    public List<BidList> findAll() {
        LOGGER.debug("getting all bids");
        return bidListRepository.findAll();
    }
    /**
     * * {@inheritDoc}
     */
    @Override
    public BidList save(BidList bid) {
        LOGGER.debug("saving bid {}", bid.getAccount());
        return bidListRepository.save(bid);
    }
    /**
     * * {@inheritDoc}
     */
    @Override
    public void delete(BidList bidList) {
        LOGGER.debug("deleting bid:{}", bidList.getBidListId());
        bidListRepository.delete(bidList);
    }
}
