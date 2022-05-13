package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;

import java.util.List;

public interface BidService {

    BidList findById(Integer id);

    List<BidList> findAll();

    BidList save(BidList bid);

    void delete(BidList bidList);
}
