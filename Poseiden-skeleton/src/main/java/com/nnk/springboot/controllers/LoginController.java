package com.nnk.springboot.controllers;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

 /*   @Autowired
    private UserService userService;*/

    private final OAuth2AuthorizedClientService authorizedClientService;

    public LoginController(OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }

/*
    @GetMapping("secure/article-details")
    public ModelAndView getAllUserArticles() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("users", userService.findAll());
        mav.setViewName("user/list");
        return mav;
    }
*/
@GetMapping("/404")
public String render404() {
    return "404";
}
   @GetMapping("/403")
    public String error() {
        return "403";
    }
}
