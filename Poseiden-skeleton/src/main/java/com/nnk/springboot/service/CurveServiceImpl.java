package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CurveServiceImpl implements CurveService {
    /**
     * SLF4J/LOG4J LOGGER instance.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(CurveServiceImpl.class);


   private final CurvePointRepository curvePointRepository;

    @Autowired
    public CurveServiceImpl(CurvePointRepository curvePointRepository) {
        this.curvePointRepository = curvePointRepository;
    }

    @Override
    public List<CurvePoint> findAll() {
        LOGGER.debug("getting all Curve Points");
        return curvePointRepository.findAll();
    }

    @Override
    public void save(CurvePoint curvePoint) {
        LOGGER.debug("saving curvePoint {}", curvePoint.getCurveId());
        curvePointRepository.save(curvePoint);
    }

    @Override
    public CurvePoint findById(Integer id) {
        LOGGER.debug("fetching CurvePoint by id:{}", id);
        return curvePointRepository.findById(id).orElseThrow(() -> {
            LOGGER.error("Invalid curve Point Id: {} ", id);
            return new IllegalArgumentException("Invalid curve Point Id:" + id);
        });
    }

    @Override
    public void delete(CurvePoint curvePoint) {
        LOGGER.debug("deleting curvePoint:{}", curvePoint.getCurveId());
        curvePointRepository.delete(curvePoint);
    }
}
