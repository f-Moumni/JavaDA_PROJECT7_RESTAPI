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
     * Create a SLF4J/LOG4J LOGGER instance.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(BidServiceImpl.class);

    @Autowired
    BidListRepository bidListRepository;

    @Override
    public BidList findById(Integer id) {
        return null;
    }

    @Override
    public List<BidList> findAll() {
        return bidListRepository.findAll();
    }

    @Override
    public BidList save(BidList bid) {
        return bidListRepository.save(bid);
    }

    @Override
    public void delete(BidList bidList) {

    }
}
