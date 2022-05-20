package com.nnk.springboot.controller;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    private final OAuth2AuthorizedClientService authorizedClientService;

    public LoginController(OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
    }

    /**
     * get login page
     * @return
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * redirect home page to login
     * @param model
     * @return
     */
    @RequestMapping("/")
    public String home(Model model) {
        return "login";
    }

    /**
     * get 403 page
     * @return
     */
    @GetMapping("/403")
    public String error() {
        return "403";
    }
}
