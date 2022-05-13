package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
@ExtendWith(MockitoExtension.class)
public class RuleNameServiceTest {
    @Mock
    private static RuleNameRepository ruleNameRepository;
    @InjectMocks
    private RuleServiceImpl ruleService;
    private RuleName ruleName;

    @BeforeEach
    void setUp() {
        ruleName = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
    }


    @Test
    void SaveRuleNameTest_shouldReturnSavedRuleName() {
        //ARRANGE
        when(ruleNameRepository.save(any(RuleName.class))).thenReturn(ruleName);
        //ACT
       RuleName result = ruleService.save(ruleName);
        //ASSERT
        assertThat(result).isEqualToComparingFieldByField(ruleName);
        verify(ruleNameRepository).save(ruleName);
    }

    @Test
    public void findByIdRuleNameTest_ShouldReturnRuleName() {
        //ARRANGE
        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleName));
        // ACT
        RuleName result = ruleService.findById(1);
        //ASSERT
        assertThat(result).isEqualToComparingFieldByField(ruleName);
    }

    @Test
    public void findByIdRuleNameTest_ShouldThrowException() {
        //ARRANGE
        when(ruleNameRepository.findById(1)).thenReturn(Optional.empty());
        // ACT //ASSERT
        assertThrows(IllegalArgumentException.class, () -> ruleService.findById(1));
    }

    @Test
    public void findAllRuleNameTest_ShouldReturnAllRuleNames() {
        //ARRANGE
        when(ruleNameRepository.findAll()).thenReturn(Arrays.asList(ruleName));
        // ACT
        List<RuleName> listResult = ruleService.findAll();
        //ASSERT
        assertThat(listResult.size()).isEqualTo(1);
    }

    @Test
    public void deleteRuleNameTest_shouldDeleteRuleName() {
        // ARRANGE
        doNothing().when(ruleNameRepository).delete(ruleName);
        // ACT
        ruleService.delete(ruleName);
        //ASSERT
        verify(ruleNameRepository).delete(ruleName);
    }
}
