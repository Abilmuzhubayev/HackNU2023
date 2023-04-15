package com.Asphyxia.HackNU2023.service;

import com.Asphyxia.HackNU2023.dao.SaleDao;
import com.Asphyxia.HackNU2023.dao.SupplyDao;
import com.Asphyxia.HackNU2023.dto.ReportDto;
import com.Asphyxia.HackNU2023.entity.Sale;
import com.Asphyxia.HackNU2023.entity.Supply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    private SaleDao saleDao;
    @Autowired
    private SupplyDao supplyDao;

    @Transactional
    public ReportDto generateReport(String barcode, String from, String to) throws ParseException {

//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date dateFrom = df.parse(from);
//        Date dateTo = df.parse(to);

//        Timestamp timeFrom  = new Timestamp(dateFrom.getTime());
//        Timestamp timeTo = new Timestamp(dateTo.getTime());

        List<Sale> sales = saleDao.getSalesByPeriod(barcode, from, to);
        List<Supply> supplies = supplyDao.getSuppliesByPeriod(barcode, to);

        int currentSupplyIndex = 0;
        long currentUsedQuantity = 0;

        long quantity = 0, revenue = 0, netProfit = 0;

        for (Sale sale : sales) {
            quantity += sale.getQuantity();
            revenue += sale.getQuantity() * sale.getPrice();
            netProfit += sale.getQuantity() * sale.getPrice();

            Supply currentSupply = currentSupplyIndex < supplies.size() ? supplies.get(currentSupplyIndex) : null;

            long left = sale.getQuantity();
            while (left > 0 && currentSupply != null && sale.getTime().compareTo(currentSupply.getTime()) > -1) {
                long leftInSupply = currentSupply.getQuantity() - currentUsedQuantity;

                netProfit -= Math.min(left, leftInSupply) * currentSupply.getPrice();

                if (left < leftInSupply) {
                    currentUsedQuantity += left;
                    left = 0;
                } else {
                    left -= leftInSupply;
                    currentSupplyIndex++;
                    currentUsedQuantity = 0;
                }
            }
        }

        return new ReportDto(barcode, quantity, revenue, netProfit);
    }

}
