package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    /**
     * SLF4J/LOG4J LOGGER instance.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(UserServiceImpl.class);


    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * * {@inheritDoc}
     */
    @Override
    public List<User> findAll() {
        LOGGER.debug("getting all users");
        return userRepository.findAll();
    }

    /**
     * * {@inheritDoc}
     */
    @Override
    public void save(User user) {
        LOGGER.debug("saving user {}", user.getFullname());
        userRepository.save(user);
    }

    /**
     * * {@inheritDoc}
     */
    @Override
    public User findById(Integer id) throws DataNotFoundException {
        LOGGER.debug("fetching user by id:{}", id);
        return userRepository.findById(id).orElseThrow(() -> {
            LOGGER.error("Invalid user Id: {} ", id);
            return new DataNotFoundException("Invalid user Id:" + id);
        });
    }

    /**
     * * {@inheritDoc}
     */
    @Override
    public void delete(User user) {
        LOGGER.debug("deleting user:{}", user.getFullname());
        userRepository.delete(user);
    }
}
