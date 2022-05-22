package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.exception.DataNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CurveService {

    /**
     * get all CurvePoint
     * @return
     */
    List<CurvePoint> findAll();

    /**
     * save given CurvePoint
     * @param curvePoint
     */
    void save(CurvePoint curvePoint);

    /**
     * find curve point by id
     * @param id
     * @return
     */
    CurvePoint findById(Integer id) throws DataNotFoundException;

    /**
     * delete a given curve point
     * @param curvePoint
     */
    void delete(CurvePoint curvePoint);
}
