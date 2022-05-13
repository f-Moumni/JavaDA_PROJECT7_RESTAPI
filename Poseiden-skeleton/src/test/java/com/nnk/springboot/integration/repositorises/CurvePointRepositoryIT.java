package com.nnk.springboot.integration.repositorises;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Sql(scripts = "classpath:test-data.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class CurvePointRepositoryIT {

    @Resource
    private CurvePointRepository curvePointRepository;
    private CurvePoint curvePoint;

    @BeforeEach
    void setUp() {
        curvePoint = new CurvePoint(10, 10d, 30d);
    }



    @Test
    public void curvePointTest_Update() {
        // Update
        curvePoint.setId(1);
        curvePoint.setCurveId(20);
        curvePoint = curvePointRepository.save(curvePoint);
        assertTrue(curvePoint.getCurveId() == 20);
    }

    @Test
    public void curvePointTest_findALL() {
        // ACT
        List<CurvePoint> listResult = curvePointRepository.findAll();
        //ASSERT
        assertThat(listResult.size() ).isEqualTo(1);
    }

    @Test
    public void curvePointTest_Delete() {
        //ARRANGE
        curvePoint.setId(1);
        //ACT
        curvePointRepository.delete(curvePoint);
        //ASSERT
        Optional<CurvePoint> curvePointList = curvePointRepository.findById(1);
        assertFalse(curvePointList.isPresent());
    }

}
