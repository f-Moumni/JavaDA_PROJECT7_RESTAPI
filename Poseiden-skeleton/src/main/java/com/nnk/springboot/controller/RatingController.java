package com.nnk.springboot.controller;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.service.RatingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/rating")
public class RatingController {
    /**
     * SLF4J/LOG4J LOGGER instance.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(RatingController.class);

    @Autowired
    RatingService ratingService;

    @GetMapping ("/list")
    public String home(Model model)
    {
        LOGGER.debug("get request rating/list");
        model.addAttribute("ratings", ratingService.findAll());
        return "rating/list";
    }

    @GetMapping("/add")
    public String addRatingForm(Rating rating) {
        LOGGER.debug("get request rating/add {}", rating.getId());
        return "rating/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        LOGGER.debug("post request rating/validate of rating{}",rating.getId());
        if (!result.hasErrors()) {
            ratingService.save(rating);
            model.addAttribute("ratings", ratingService.findAll());
            return "redirect:/rating/list";
        }
        LOGGER.error("result error :{}",result.getFieldError());
        return "rating/add";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        LOGGER.debug("get request rating/update/{}",id);
        Rating rating = ratingService.findById(id);
        model.addAttribute("rating", rating);
        return "rating/update";
    }

    @PostMapping("/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model) {
        LOGGER.debug("post request rating/update/{}",id);
        if (result.hasErrors()) {
            LOGGER.error("result error :{}",result.getFieldError());
            return "rating/update";
        }
        rating.setId(id);
        ratingService.save(rating);
        model.addAttribute("ratings", ratingService.findAll());
        return "redirect:/rating/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        LOGGER.debug("get request rating/delete/{}",id);
        Rating rating = ratingService.findById(id);
        ratingService.delete(rating);
        model.addAttribute("ratings", ratingService.findAll());
        return "redirect:/rating/list";
    }
}
