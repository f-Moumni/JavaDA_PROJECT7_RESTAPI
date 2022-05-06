package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CurveService {
    List<CurvePoint> findAll();

    void save(CurvePoint curvePoint);

    CurvePoint findById(Integer id);

    void delete(CurvePoint curvePoint);
}
