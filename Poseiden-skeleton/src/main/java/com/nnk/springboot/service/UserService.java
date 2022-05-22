package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.exception.DataNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    /**
     * get all users
     * @return
     */
    List<User> findAll();

    /**
     * save given user
     * @param user
     */
    void save(User user);

    /**
     * find user by id
     * @param id
     * @return
     */
    User findById(Integer id) throws DataNotFoundException;

    /**
     * delete given user
     * @param user
     */
    void delete(User user);
}
