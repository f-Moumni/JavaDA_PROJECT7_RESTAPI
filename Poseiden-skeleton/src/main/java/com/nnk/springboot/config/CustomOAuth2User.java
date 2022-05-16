package com.nnk.springboot.config;

import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomOAuth2User implements OAuth2User {

    /**
     * SLF4J/LOG4J LOGGER instance.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(CustomOAuth2User.class);

    /** OAuth2user**/

    private OAuth2User oauth2User;


    /**
     * CustomOAuth2User constructor
     * @param oauth2User
     */
    public CustomOAuth2User(OAuth2User oauth2User) {

        this.oauth2User = oauth2User;
    }


    /**
     * * {@inheritDoc}
     */

    @Override
    public Map<String, Object> getAttributes() {
        LOGGER.debug("getting 0Auth2User attributes ");
        return oauth2User.getAttributes();
    }

    /**
     * * {@inheritDoc}
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        LOGGER.debug("getting 0Auth2User authorities");
        return oauth2User.getAuthorities();
    }

    /**
     * Get the OAuth 2.0 token attribute login
     * Returns:
     * the attribute login or null otherwise
     */
    @Override
    public String getName() {
        LOGGER.debug("getting 0Auth2User login");
        return oauth2User.getAttribute("login");
    }

}