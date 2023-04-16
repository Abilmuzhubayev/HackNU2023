package com.Asphyxia.HackNU2023.service;

import com.Asphyxia.HackNU2023.dao.CursorsDao;
import com.Asphyxia.HackNU2023.dao.PrefixSumsByDateDao;
import com.Asphyxia.HackNU2023.dao.SupplyDao;
import com.Asphyxia.HackNU2023.dto.SaleDto;
import com.Asphyxia.HackNU2023.dto.SupplyDto;
import com.Asphyxia.HackNU2023.entity.BarcodeCursor;
import com.Asphyxia.HackNU2023.entity.SaleToSupplyCursor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

@Service
public class SupplyService {

    @Autowired
    private SupplyDao supplyDao;
    @Autowired
    private ReportService reportService;
    @Autowired
    private CursorsDao cursorsDao;
    @Autowired
    private PrefixSumsByDateDao prefixSumsByDateDao;

    @Transactional
    public void deleteSupply(Long id) {
        SupplyDto supplyDto = supplyDao.getSupplyById(id);

        SaleToSupplyCursor cursor = cursorsDao.getPrevByTime(supplyDto.getBarcode(), supplyDto.getSupplyTime());
        String saleTime = cursor == null ? "1971-01-01 00:00:00" : cursor.getSaleTime().toString();

        supplyDao.deleteSupply(id);
        reportService.recalculate(supplyDto.getBarcode(), saleTime, supplyDto.getSupplyTime());
    }

    @Transactional
    public void editSupply(SupplyDto supplyDto) {
        SupplyDto oldSupplyDto = supplyDao.getSupplyById(supplyDto.getId());
        Timestamp oldTime = Timestamp.valueOf(oldSupplyDto.getSupplyTime());
        Timestamp newTime = Timestamp.valueOf(supplyDto.getSupplyTime());

        SaleToSupplyCursor cursor = cursorsDao.getPrevByTime(supplyDto.getBarcode(), supplyDto.getSupplyTime());
        if (oldTime.compareTo(newTime) < 0)
            cursor = cursorsDao.getPrevByTime(oldSupplyDto.getBarcode(), oldSupplyDto.getSupplyTime());
        String saleTime = cursor == null ? "1971-01-01 00:00:00" : cursor.getSaleTime().toString();

        supplyDao.editSupply(supplyDto);
        reportService.recalculate(supplyDto.getBarcode(), saleTime, supplyDto.getSupplyTime());
    }

    @Transactional
    public Long addSupply(SupplyDto supplyDto) {

        SaleToSupplyCursor cursor = cursorsDao.getPrevByTime(supplyDto.getBarcode(), supplyDto.getSupplyTime());
        String saleTime = cursor == null ? "1971-01-01 00:00:00" : cursor.getSaleTime().toString();

        Long result = supplyDao.addSupply(supplyDto);
        reportService.recalculate(supplyDto.getBarcode(), saleTime, supplyDto.getSupplyTime());

        return result;
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
