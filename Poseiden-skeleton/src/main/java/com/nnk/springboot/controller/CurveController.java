package com.nnk.springboot.controller;

import com.nnk.springboot.domain.CurvePoint;

import com.nnk.springboot.service.CurveService;
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

/**
 * Curve Controller
 */
@Controller
@RequestMapping("/curvePoint")
public class CurveController {

    /**
     * SLF4J/LOG4J LOGGER instance.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(CurveController.class);

    /**
     * Curve Service Instance
     */
    @Autowired
    CurveService curveService;

    /**
     * get methode for get all curvePoints
     * @param model
     * @return
     */
    @GetMapping("/list")
    public String home(Model model)
    {
        LOGGER.debug("get request curvePoint/list");
        model.addAttribute("curvePoints", curveService.findAll());
        return "curvePoint/list";
    }

    /**
     * get methode to get add curvePoint view
     * @param bid
     * @return
     */
    @GetMapping("/add")
    public String addBidForm(CurvePoint bid) {
        LOGGER.debug("get request curvePoint/add {}", bid.getCurveId());
        return "curvePoint/add";
    }

    /**
     * post methode to add curvePoint
     * @param curvePoint
     * @param result
     * @param model
     * @return
     */
    @PostMapping("/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        LOGGER.debug("post request curvePoint/validate of curvePoint{}",curvePoint.getCurveId());
        if (!result.hasErrors()) {
            curveService.save(curvePoint);
            model.addAttribute("curvePoints", curveService.findAll());
            return "redirect:/curvePoint/list";
        }
        LOGGER.error("result error :{}",result.getFieldError());
        return "curvePoint/add";
    }

    /**
     * get methode to get the update view
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        LOGGER.debug("get request curvePoint/update/{}",id);
        CurvePoint curvePoint  = curveService.findById(id);
        model.addAttribute("curvePoint", curvePoint);
        return "curvePoint/update";
    }

    /**
     * post methode to update the curvePoint
     * @param id
     * @param curvePoint
     * @param result
     * @param model
     * @return
     */
    @PostMapping("/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                             BindingResult result, Model model) {
        LOGGER.debug("post request curvePoint/update/{}",id);
        if (result.hasErrors()) {
            LOGGER.error("result error :{}",result.getFieldError());
            return "curvePoint/update";
        }
        curvePoint.setCurveId(id);
        curveService.save(curvePoint);
        model.addAttribute("curvePoints", curveService.findAll());
        return "redirect:/curvePoint/list";
    }

    /**
     * get methode to delete curve Point
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        LOGGER.debug("get request curvePoint/delete/{}",id);
        CurvePoint curvePoint  = curveService.findById(id);
        curveService.delete(curvePoint);
        model.addAttribute("curvePoints", curveService.findAll());
        return "redirect:/curvePoint/list";
    }
}
