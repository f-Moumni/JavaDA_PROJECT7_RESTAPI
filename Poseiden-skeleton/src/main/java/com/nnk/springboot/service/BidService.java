package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.exception.DataNotFoundException;

import java.util.List;

/**
 * BidService class to Manage Bid
 */
public interface BidService {
    /**
     * get bid by id
     * @param id
     * @return Bid
     */
    BidList findById(Integer id) throws DataNotFoundException;

    /**
     * get all bids
     * @return
     */
    List<BidList> findAll();

    /**
     * save given bid
     * @param bid
     * @return
     */
    BidList save(BidList bid);

    /**
     * delete a given bid
     * @param bidList
     */
    void delete(BidList bidList);
}
