package com.Asphyxia.HackNU2023.service;

import com.Asphyxia.HackNU2023.dao.CursorsDao;
import com.Asphyxia.HackNU2023.dao.PrefixSumsByDateDao;
import com.Asphyxia.HackNU2023.dao.SaleDao;
import com.Asphyxia.HackNU2023.dto.SaleDto;
import com.Asphyxia.HackNU2023.entity.BarcodeCursor;
import com.Asphyxia.HackNU2023.entity.PrefixSumsByDate;
import com.Asphyxia.HackNU2023.entity.SaleToSupplyCursor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

@Service
public class SaleService {

    @Autowired
    private SaleDao saleDao;
    @Autowired
    private ReportService reportService;
    @Autowired
    private CursorsDao cursorsDao;
    @Autowired
    private PrefixSumsByDateDao prefixSumsByDateDao;

    @Transactional
    public void deleteSale(Long id) {
        SaleDto sale = saleDao.getSaleById(id);

        SaleToSupplyCursor cursor = cursorsDao.getBySaleTime(sale.getBarcode(), sale.getSaleTime());
        cursorsDao.delete(cursor);

        BigInteger barcode = cursor.getBarcode();
        String saleTime = cursor.getSaleTime().toString();

        PrefixSumsByDate prefixSums = prefixSumsByDateDao.getByDate(sale.getSaleTime(), sale.getBarcode());
        prefixSumsByDateDao.delete(prefixSums);

        cursor = cursorsDao.getNextByTime(sale.getBarcode(), sale.getSaleTime());
        if (cursor == null) {
            BarcodeCursor barcodeCursor = cursorsDao.getByBarcode(sale.getBarcode());
            cursorsDao.delete(barcodeCursor);
        }

        saleDao.deleteSale(id);

        reportService.recalculate(barcode, saleTime, null);
    }

    @Transactional
    public void editSale(SaleDto saleDto) {
        Timestamp newTime = Timestamp.valueOf(saleDto.getSaleTime());
        Timestamp oldTime;

        SaleToSupplyCursor cursor = cursorsDao.getBySaleTime(saleDto.getBarcode(), saleDto.getSaleTime());
        if (cursor == null)
            throw new IllegalStateException("There is no cursor for sale, but has to be");
        oldTime = cursor.getSaleTime();
        cursor.setSaleTime(newTime);
        cursorsDao.set(cursor);

        PrefixSumsByDate prefixSums = prefixSumsByDateDao.getByDate(oldTime.toString(), saleDto.getBarcode());
        prefixSums.setTime(newTime);
        prefixSumsByDateDao.set(prefixSums);

        saleDao.editSale(saleDto);

        reportService.recalculate(saleDto.getBarcode(), (newTime.compareTo(oldTime) < 0 ? newTime : oldTime).toString(), null);
    }

    @Transactional
    public Long addSale(SaleDto saleDto) {
        long result = saleDao.addSale(saleDto);

        SaleToSupplyCursor cursor = cursorsDao.getNextByTime(saleDto.getBarcode(), saleDto.getSaleTime());
        String supplyTime = "1971-01-01 00:00:00";
        long usedQuantity = 0;

        if (cursor != null) {
            supplyTime = cursor.getSupplyTime().toString();
            usedQuantity = cursor.getQuantityUsed();
        } else {
            BarcodeCursor barcodeCursor = cursorsDao.getByBarcode(saleDto.getBarcode());
            if (barcodeCursor != null) {
                supplyTime = barcodeCursor.getSupplyTime().toString();
                usedQuantity = barcodeCursor.getQuantityUsed();
            }
        }

        cursor = new SaleToSupplyCursor();
        cursor.setSaleTime(Timestamp.valueOf(saleDto.getSaleTime()));
        cursor.setBarcode(saleDto.getBarcode());
        cursor.setSupplyTime(Timestamp.valueOf(supplyTime));
        cursor.setQuantityUsed(usedQuantity);
        cursorsDao.set(cursor);

        BarcodeCursor barcodeCursor = cursorsDao.getByBarcode(saleDto.getBarcode());
        if (barcodeCursor == null) {
            barcodeCursor = new BarcodeCursor();
            barcodeCursor.setBarcode(saleDto.getBarcode());
            cursorsDao.set(barcodeCursor);
        }

        PrefixSumsByDate prefixSums = new PrefixSumsByDate();
        prefixSums.setBarcode(saleDto.getBarcode());
        prefixSums.setTime(Timestamp.valueOf(saleDto.getSaleTime()));
        prefixSumsByDateDao.set(prefixSums);

        reportService.recalculate(saleDto.getBarcode(), saleDto.getSaleTime(), null);
        return result;
    }

    @Transactional
    public SaleDto getSaleById(Long id) {
        return saleDao.getSaleById(id);
    }

    public List<SaleDto> getSalesByPeriod(BigInteger barcode, String from, String to) {
        return saleDao.getSalesByPeriod(barcode, from, to);
    }

}
