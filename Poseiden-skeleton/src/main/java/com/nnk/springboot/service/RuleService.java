package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RuleService {

    /**
     * find all rules
     * @return
     */
    List<RuleName> findAll();

    /**
     *
     * @param ruleName
     * @return
     */
    RuleName save(RuleName ruleName);

    RuleName findById(Integer id);

    void delete(RuleName rule);
}
