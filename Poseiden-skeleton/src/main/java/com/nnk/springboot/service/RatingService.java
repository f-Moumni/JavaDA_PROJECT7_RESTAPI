package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.exception.DataNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RatingService {

    /**
     * get all rating
     * @return
     */
    List<Rating> findAll();

    /**
     * save given rating
     * @param rating
     * @return
     */
    Rating save(Rating rating);

    /**
     * find rating by id
     * @param id
     * @return
     */
    Rating findById(Integer id) throws DataNotFoundException;


    /**
     * delete given rating
     * @param rating
     */
    void delete(Rating rating);
}
