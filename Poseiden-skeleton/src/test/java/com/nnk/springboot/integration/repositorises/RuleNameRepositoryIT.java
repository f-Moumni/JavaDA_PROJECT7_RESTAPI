package com.nnk.springboot.integration.repositorises;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Sql(scripts = "classpath:test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class RuleNameRepositoryIT {

    @Autowired
    private RuleNameRepository ruleNameRepository;
    private RuleName rule;

    @BeforeEach
    void setUp() {

        rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
    }

    @Test
    public void ruleTest_update() {
        //ARRANGE
        rule.setId(1);
        rule.setName("Rule Name Update");
        //ACT
        rule = ruleNameRepository.save(rule);
        //ASSERT
        assertTrue(rule.getName().equals("Rule Name Update"));
    }

    @Test
    public void ruleTest_FindALL() {
        //ACT
        List<RuleName> listResult = ruleNameRepository.findAll();
        //ASSERT
        assertThat(listResult.size()).isEqualTo(2);
    }

    @Test
    public void ruleTest_Delete() {
        //ARRANGE
        rule.setId(1);
        //ACT
        ruleNameRepository.delete(rule);
        Optional<RuleName> ruleList = ruleNameRepository.findById(1);
        //ASSERT
        assertFalse(ruleList.isPresent());
    }
}
