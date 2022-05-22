package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RuleServiceImpl implements RuleService {
    /**
     * SLF4J/LOG4J LOGGER instance.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(RuleServiceImpl.class);


    private final RuleNameRepository ruleNameRepository;

    @Autowired
    public RuleServiceImpl(RuleNameRepository ruleNameRepository) {
        this.ruleNameRepository = ruleNameRepository;
    }

    /**
     * * {@inheritDoc}
     */
    @Override
    public List<RuleName> findAll() {
        LOGGER.debug("getting all rules");
        return ruleNameRepository.findAll();
    }
    /**
     * * {@inheritDoc}
     */
    @Override
    public RuleName save(RuleName ruleName) {
        LOGGER.debug("saving RuleName {}", ruleName.getName());
        return ruleNameRepository.save(ruleName);
    }
    /**
     * * {@inheritDoc}
     */
    @Override
    public RuleName findById(Integer id) throws DataNotFoundException {
        LOGGER.debug("fetching RuleName by id:{}", id);
        return ruleNameRepository.findById(id).orElseThrow(() -> {
            LOGGER.error("Invalid RuleName Id: {} ", id);
            return new DataNotFoundException("Invalid RuleName Id:" + id);
        });
    }

    /**
     * * {@inheritDoc}
     */
    @Override
    public void delete(RuleName rule) {
        LOGGER.debug("deleting ruleName:{}", rule.getName());
        ruleNameRepository.delete(rule);
    }
}
