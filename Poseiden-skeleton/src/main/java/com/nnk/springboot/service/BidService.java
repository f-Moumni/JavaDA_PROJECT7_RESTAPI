package com.nnk.springboot.service;

import com.nnk.springboot.controllers.UserController;
import com.nnk.springboot.domain.BidList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public interface BidService {

    BidList findById(Integer id);

    List<BidList> findAll();

    BidList save(BidList bid);

    void delete(BidList bidList);
}
