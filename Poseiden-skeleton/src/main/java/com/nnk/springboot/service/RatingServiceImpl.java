package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RatingServiceImpl implements RatingService {

    /**
     * SLF4J/LOG4J LOGGER instance.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(RatingServiceImpl.class);

    @Autowired
    RatingRepository ratingRepository;

    /**
     * * {@inheritDoc}
     */
    @Override
    public List<Rating> findAll() {
        LOGGER.debug("getting all ratings");
        return ratingRepository.findAll();
    }
    /**
     * * {@inheritDoc}
     */
    @Override
    public Rating save(Rating rating) {
        LOGGER.debug("saving rating {}",rating.getId());
        return ratingRepository.save(rating);
    }
    /**
     * * {@inheritDoc}
     */
    @Override
    public Rating findById(Integer id) {
        LOGGER.debug("fetching Rating by id:{}",id);
        return   ratingRepository.findById(id).orElseThrow( ()->{
            LOGGER.error("Invalid Rating Id: {} ", id);
            return new IllegalArgumentException("Invalid Rating Id:" + id);
        }   );
    }
    /**
     * * {@inheritDoc}
     */
    @Override
    public void delete(Rating rating) {
        LOGGER.debug("deleting rating :{}",rating.getId());
        ratingRepository.delete(rating);
    }
}
