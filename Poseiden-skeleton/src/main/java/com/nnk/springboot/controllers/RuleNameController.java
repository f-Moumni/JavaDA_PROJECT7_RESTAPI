package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.service.BidService;
import com.nnk.springboot.service.RuleService;
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
@RequestMapping("/ruleName")
public class RuleNameController {
    /**
     * SLF4J/LOG4J LOGGER instance.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(RuleNameController.class);

    @Autowired
    RuleService ruleService;

    @GetMapping("/list")
    public String home(Model model)
    {
        LOGGER.debug("get request ruleName/list");
        model.addAttribute("rules", ruleService.findAll());
        return "ruleName/list";
    }

    @GetMapping("/add")
    public String addRuleForm(RuleName bid) {
        LOGGER.debug("get request ruleName/add {}", bid.getName());
        return "ruleName/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        LOGGER.debug("post request ruleName/validate of rule{}",ruleName.getName());
        if (!result.hasErrors()) {
            ruleService.save(ruleName);
            model.addAttribute("rules", ruleService.findAll());
            return "redirect:/ruleName/list";
        }
        LOGGER.error("result error :{}",result.getFieldError());
        return "ruleName/add";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        LOGGER.debug("get request ruleName/update/{}",id);
        RuleName rule = ruleService.findById(id);
        model.addAttribute("rule", rule);
        return "ruleName/update";
    }

    @PostMapping("/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                             BindingResult result, Model model) {
        LOGGER.debug("post request ruleName/update/{}",id);
        if (result.hasErrors()) {
            LOGGER.error("result error :{}",result.getFieldError());
            return "ruleName/update";
        }
        ruleName.setId(id);
        ruleService.save(ruleName);
        model.addAttribute("rules", ruleService.findAll());
        return "redirect:/ruleName/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        LOGGER.debug("get request ruleName/delete/{}",id);
        RuleName rule = ruleService.findById(id);
        ruleService.delete(rule);
        model.addAttribute("rules", ruleService.findAll());
        return "redirect:/ruleName/list";
    }
}
