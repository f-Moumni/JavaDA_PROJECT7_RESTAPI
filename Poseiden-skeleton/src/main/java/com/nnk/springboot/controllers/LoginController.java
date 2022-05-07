package com.nnk.springboot.controllers;

import com.nnk.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("secure/article-details")
    public ModelAndView getAllUserArticles() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("users", userService.findAll());
        mav.setViewName("user/list");
        return mav;
    }

   @GetMapping("/403")
    public String error() {
  /*      ModelAndView mav = new ModelAndView("403");
        String errorMessage= "You are not authorized for the requested data.";
        mav.addObject("errorMsg", errorMessage);*/
       // mav.setViewName("403");
        return "403";
    }
}
