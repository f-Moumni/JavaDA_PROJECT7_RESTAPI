package com.nnk.springboot.controller;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.service.TradeService;
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
@RequestMapping("/trade")
public class TradeController {

    /**
     * SLF4J/LOG4J LOGGER instance.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(TradeController.class);

    /**
     * TradeService Instance
     */
    @Autowired
    private TradeService tradeService;

    /**
     * get methode to get all Trade
     * @param model
     * @return
     */
    @GetMapping("/list")
    public String home(Model model)
    {
        LOGGER.debug("get request trade/list");
        model.addAttribute("trades", tradeService.findAll());
        return "trade/list";
    }

    /**
     * get methode to get add view
     * @param bid
     * @return
     */
    @GetMapping("/add")
    public String addUser(Trade bid) {
        LOGGER.debug("get request trade/add of {}",bid.getTradeId());
        return "trade/add";
    }


    /**
     * post methode to add new Trade
     * @param trade
     * @param result
     * @param model
     * @return
     */
    @PostMapping("/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        LOGGER.debug("post request trade/validate of trade{}",trade.getTradeId());
        if (!result.hasErrors()) {
            tradeService.save(trade);
            model.addAttribute("trades", tradeService.findAll());
            return "redirect:/trade/list";
        }
        LOGGER.error("result error :{}",result.getFieldError());
        return "trade/add";
    }

    /**
     * get methode to show update view
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        LOGGER.debug("get request trade/update/{}",id);
        Trade trade = tradeService.findById(id);
        model.addAttribute("trade", trade);
        return "trade/update";
    }

    /**
     * post methode to update Trade
     * @param id
     * @param trade
     * @param result
     * @param model
     * @return
     */
    @PostMapping("/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                             BindingResult result, Model model) {
        LOGGER.debug("post request trade/update/{}",id);
        if (result.hasErrors()) {
            LOGGER.error("result error :{}",result.getFieldError());
            return "trade/update";
        }
        trade.setTradeId(id);
        tradeService.save(trade);
        model.addAttribute("trades", tradeService.findAll());
        return "redirect:/trade/list";
    }

    /**
     * get methode to delete trade
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        LOGGER.debug("get request trade/delete/{}",id);
        Trade trade = tradeService.findById(id);
        tradeService.delete(trade);
        model.addAttribute("trades", tradeService.findAll());
        return "redirect:/trade/list";
    }
}
