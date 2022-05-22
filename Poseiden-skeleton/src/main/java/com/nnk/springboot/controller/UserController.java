package com.nnk.springboot.controller;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {
    /**
     * SLF4J/LOG4J LOGGER instance.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(UserController.class);

    /**
     * UserService instance
     */
    @Autowired
    private UserService userService;


    /**
     * get methode to get all users
     * @param model
     * @return
     */
    @GetMapping("/list")
    public String home(Model model)
    {
        LOGGER.debug("get request user/list");
        model.addAttribute("users", userService.findAll());
        return "user/list";
    }

    /**
     * get methode to show add view
     * @param bid
     * @return
     */
    @GetMapping("/add")
    public String addUser(User bid) {
        LOGGER.debug("get request user/add of {}",bid.getFullname());
        return "user/add";
    }

    /**
     * post methode to add new User
     * @param user
     * @param result
     * @param model
     * @return
     */
    @PostMapping("/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
        LOGGER.debug("post request user/validate of {}",user.getFullname());
        if (!result.hasErrors()) {
           BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            userService.save(user);
            model.addAttribute("users", userService.findAll());
            return "redirect:/user/list";
        }
        LOGGER.error("result error :{}",result.getFieldError());
        return "user/add";
    }

    /**
     * get methode to show update view
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) throws DataNotFoundException {
        LOGGER.debug("get request user/update/{}",id);
        User user = userService.findById(id);
        user.setPassword("");
        model.addAttribute("user", user);
        return "user/update";
    }


    /**
     * post methode to update user
     * @param id
     * @param user
     * @param result
     * @param model
     * @return
     */
    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) {
        LOGGER.debug("post request user/update/{}",id);
        if (result.hasErrors()) {
            LOGGER.error("result Error :{}",result.getFieldError());
            return "user/update";
        }

       BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setId(id);
        userService.save(user);
        model.addAttribute("users", userService.findAll());
        return "redirect:/user/list";
    }

    /**
     * get methode to delete user
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) throws DataNotFoundException {
        LOGGER.debug("Get request user/delete/{}",id);
        User user = userService.findById(id);
        userService.delete(user);
        model.addAttribute("users", userService.findAll());
        return "redirect:/user/list";
    }
}
