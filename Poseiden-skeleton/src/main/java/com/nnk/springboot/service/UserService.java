package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;
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
    User findById(Integer id);

    /**
     * delete given user
     * @param user
     */
    void delete(User user);
}
