package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;

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
    BidList findById(Integer id);

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
