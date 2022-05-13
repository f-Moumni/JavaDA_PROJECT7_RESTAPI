package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
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

@ExtendWith(MockitoExtension.class)
public class CurvePointServiceTest {

    @Mock
    private static CurvePointRepository curvePointRepository;
    @InjectMocks
    private CurveServiceImpl curveService;
    private CurvePoint curvePoint;

    @BeforeEach
    void setUp() {
        curvePoint = new CurvePoint(10, 10d, 30d);
    }

    @Test
    void SaveCurvePointTest_shouldReturnSavedCurvePoint() {
        //ARRANGE
        when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(curvePoint);
        //ACT
        curveService.save(curvePoint);
        //ASSERT
        verify(curvePointRepository).save(curvePoint);
    }

    @Test
    public void findByIdCurvePointTest_ShouldReturnCurvePoint() {
        //ARRANGE
        when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));
        // ACT
        CurvePoint result = curveService.findById(1);
        //ASSERT
        assertThat(result).isEqualToComparingFieldByField(curvePoint);
    }

    @Test
    public void findByIdCurvePointTest_ShouldThrowException() {
        //ARRANGE
        when(curvePointRepository.findById(1)).thenReturn(Optional.empty());
        // ACT //ASSERT
        assertThrows(IllegalArgumentException.class, () -> curveService.findById(1));
    }

    @Test
    public void findAllCurvePointTest_ShouldReturnAllCurvePoints() {
        //ARRANGE
        when(curvePointRepository.findAll()).thenReturn(Arrays.asList(curvePoint));
        // ACT
        List<CurvePoint> listResult = curveService.findAll();
        //ASSERT
        assertThat(listResult.size()).isEqualTo(1);
    }

    @Test
    public void deleteCurvePointTest_shouldDeleteCurvePoint() {
        // ARRANGE
        doNothing().when(curvePointRepository).delete(curvePoint);
        // ACT
        curveService.delete(curvePoint);
        //ASSERT
        verify(curvePointRepository).delete(curvePoint);
    }
}
