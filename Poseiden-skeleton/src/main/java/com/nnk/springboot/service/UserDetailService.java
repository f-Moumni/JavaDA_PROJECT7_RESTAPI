package com.nnk.springboot.service;

import com.nnk.springboot.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserDetailService implements UserDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailService.class);
    private final static String USER_NOT_FOUND_MSG = "user with %S not found";

    private final UserRepository userRepository;

    @Autowired
    public UserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.debug(" loading user by username {}",username);
        com.nnk.springboot.domain.User user = userRepository.findByUsername(username)
                .orElseThrow(() ->{LOGGER.error(String.format(USER_NOT_FOUND_MSG, username));
                    return  new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, username));
                });
        LOGGER.debug("user {} with role {} loaded ",username ,user.getRole());
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
        return new User(user.getUsername(), user.getPassword(), Arrays.asList(authority));

    }
}
