package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    Object findAll();

    void save(User user);

    User findById(Integer id);

    void delete(User user);
}
