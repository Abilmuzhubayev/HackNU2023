package com.Asphyxia.HackNU2023.service;

import com.Asphyxia.HackNU2023.dao.SupplyDao;
import com.Asphyxia.HackNU2023.dto.SaleDto;
import com.Asphyxia.HackNU2023.dto.SupplyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

@Service
public class SupplyService {

    @Autowired
    private SupplyDao supplyDao;

    @Transactional
    public void deleteSupply(Long id) {
        supplyDao.deleteSupply(id);
    }
    @Transactional
    public void editSupply(SupplyDto supplyDto) {
        supplyDao.editSupply(supplyDto);
    }
    @Transactional
    public Long addSupply(SupplyDto supplyDto) {
        return supplyDao.addSupply(supplyDto);
    }
    @Transactional
    public SupplyDto getSupplyById(Long id) {
        return supplyDao.getSupplyById(id);
    }

    @Transactional
    public List<SupplyDto> getSuppliesByPeriod(BigInteger barcode, String from, String to) {
        return supplyDao.getSuppliesByPeriod(barcode, from, to);
    }

}
