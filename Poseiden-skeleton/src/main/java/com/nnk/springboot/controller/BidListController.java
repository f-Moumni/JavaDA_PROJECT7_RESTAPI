package com.nnk.springboot.controller;

import com.nnk.springboot.domain.BidList;

import com.nnk.springboot.service.BidService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequestMapping("/bidList")
public class BidListController {
    /**
     * SLF4J/LOG4J LOGGER instance.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(BidListController.class);


    private final BidService bidService;

    @Autowired
    public BidListController(BidService bidService) {
        this.bidService = bidService;
    }

    @GetMapping("/list")
    public String home(Model model) {
        LOGGER.debug("get request bidList/list");
        model.addAttribute("bids", bidService.findAll());
        return "bidList/list";
    }

    @GetMapping("/add")
    public String addBidForm(BidList bid) {
        LOGGER.debug("get request bidList/add {}", bid.getBidListId());
        return "bidList/add";
    }

    @PostMapping("/validate")
    public String validate( @Valid BidList bid, BindingResult result, Model model) {
        LOGGER.debug("post request bidList/validate of bid{}", bid.getBidListId());
        if (!result.hasErrors()) {
            bidService.save(bid);
            model.addAttribute("bids", bidService.findAll());
            return "redirect:/bidList/list";
        }
        LOGGER.error("result error :{}", result.getFieldError());
        return "bidList/add";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        LOGGER.debug("get request bidList/update/{}", id);
        BidList bidList = bidService.findById(id);
        model.addAttribute("bidList", bidList);
        return "bidList/update";
    }

    @PostMapping("/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                            BindingResult result, Model model) {
        LOGGER.debug("post request bidList/update/{}", id);
        if (result.hasErrors()) {
            LOGGER.error("result error :{}", result.getFieldError());
            return "bidList/update";
        }
        bidList.setBidListId(id);
        bidService.save(bidList);
        model.addAttribute("bids", bidService.findAll());
        return "redirect:/bidList/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        LOGGER.debug("get request bidList/delete/{}", id);
        BidList bidList = bidService.findById(id);
        bidService.delete(bidList);
        model.addAttribute("bids", bidService.findAll());
        return "redirect:/bidList/list";
    }
}