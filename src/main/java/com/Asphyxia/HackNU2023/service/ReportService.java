package com.Asphyxia.HackNU2023.service;

import com.Asphyxia.HackNU2023.dao.SaleDao;
import com.Asphyxia.HackNU2023.dao.SupplyDao;
import com.Asphyxia.HackNU2023.dto.ReportDto;
import com.Asphyxia.HackNU2023.entity.Sale;
import com.Asphyxia.HackNU2023.entity.Supply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    private SaleDao saleDao;
    @Autowired
    private SupplyDao supplyDao;

    @Transactional
    public ReportDto generateReport(BigInteger barcode, String from, String to) throws ParseException {

        List<Sale> sales = saleDao.getSalesByPeriod(barcode, to);
        List<Supply> supplies = supplyDao.getSuppliesByPeriod(barcode, to);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateFrom = df.parse(from);
        Timestamp timeFrom = new Timestamp(dateFrom.getTime());

        int currentSupplyIndex = 0;
        long currentUsedQuantity = 0;

        long quantity = 0, revenue = 0, netProfit = 0;

        for (Sale sale : sales) {
            if (sale.getTime().compareTo(timeFrom) > -1) {
                quantity += sale.getQuantity();
                revenue += sale.getQuantity() * sale.getPrice();
                netProfit += sale.getQuantity() * sale.getPrice();
            }

            Supply currentSupply = currentSupplyIndex < supplies.size() ? supplies.get(currentSupplyIndex) : null;

            long left = sale.getQuantity();
            while (left > 0 && currentSupply != null && sale.getTime().compareTo(currentSupply.getTime()) > -1) {
                long leftInSupply = currentSupply.getQuantity() - currentUsedQuantity;

                if (sale.getTime().compareTo(timeFrom) > -1)
                    netProfit -= Math.min(left, leftInSupply) * currentSupply.getPrice();


                if (left < leftInSupply) {
                    currentUsedQuantity += left;
                    left = 0;
                } else {
                    left -= leftInSupply;
                    currentSupplyIndex++;
                    currentUsedQuantity = 0;
                    currentSupply = currentSupplyIndex < supplies.size() ? supplies.get(currentSupplyIndex) : null;
                }
            }
        }

        return new ReportDto(barcode, quantity, revenue, netProfit);
    }

}
