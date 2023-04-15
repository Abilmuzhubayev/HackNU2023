package com.Asphyxia.HackNU2023.service;

import com.Asphyxia.HackNU2023.dao.SaleDao;
import com.Asphyxia.HackNU2023.dto.SaleDto;
import com.Asphyxia.HackNU2023.entity.Sale;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

@Service
public class SaleService {

    @Autowired
    private SaleDao saleDao;
    @Transactional
    public void deleteSale(Long id) {
        saleDao.deleteSale(id);
    }

    @Transactional
    public void editSale(SaleDto saleDto) {
        saleDao.editSale(saleDto);
    }

    @Transactional
    public Long addSale(SaleDto saleDto) {
        return saleDao.addSale(saleDto);
    }

    @Transactional
    public SaleDto getSaleById(Long id) {
        return saleDao.getSaleById(id);
    }

    public List<SaleDto> getSalesByPeriod(BigInteger barcode, String from, String to) {
        return saleDao.getSalesByPeriod(barcode, from, to);
    }

}
