package com.Asphyxia.HackNU2023.service;

import com.Asphyxia.HackNU2023.dao.*;
import com.Asphyxia.HackNU2023.dto.ReportDto;
import com.Asphyxia.HackNU2023.entity.*;
import org.modelmapper.internal.bytebuddy.build.ToStringPlugin;
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
    @Autowired
    private PrefixSumsByDateDao prefixSumsSalesDao;
    @Autowired
    private CursorsDao cursorDao;

    @Transactional
    public ReportDto generateSlowReport(BigInteger barcode, String from, String to) throws ParseException {

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

    @Transactional
    public ReportDto generateFastReport(BigInteger barcode, String from, String to) {

        PrefixSumsByDate lastSale = prefixSumsSalesDao.getLastByDate(to, barcode, false);
        PrefixSumsByDate saleBefore = prefixSumsSalesDao.getLastByDate(from, barcode, true);

        long quantity = (lastSale != null ? lastSale.getQuantitySum() : 0) - (saleBefore != null ? saleBefore.getQuantitySum() : 0);
        long revenue = (lastSale != null ? lastSale.getRevenueSum() : 0) - (saleBefore != null ? saleBefore.getRevenueSum() : 0);
        long netProfit = (lastSale != null ? lastSale.getNetProfitSum() : 0) - (saleBefore != null ? saleBefore.getNetProfitSum() : 0);
        return new ReportDto(barcode, quantity, revenue, netProfit);

    }

    @Transactional
    public void recalculate(BigInteger barcode, String saleTime, String minSupplyTime) {

        String supplyTime = "1971-01-01 00:00:00";
        long usedQuantity = 0;

        SaleToSupplyCursor cursor = cursorDao.getNextByTime(barcode, saleTime);
        if (cursor != null) {
            supplyTime = cursor.getSupplyTime() != null ? cursor.getSupplyTime().toString() : "1971-01-01 00:00:00";
            usedQuantity = cursor.getQuantityUsed() != null ? cursor.getQuantityUsed() : 0;
        } else {
            BarcodeCursor barcodeCursor = cursorDao.getByBarcode(barcode);
            if (barcodeCursor != null) {
                supplyTime = barcodeCursor.getSupplyTime() != null ? barcodeCursor.getSupplyTime().toString() : "1971-01-01 00:00:00";
                usedQuantity = barcodeCursor.getQuantityUsed() != null ? barcodeCursor.getQuantityUsed() : 0;
            }
        }
        if (minSupplyTime != null && Timestamp.valueOf(supplyTime).compareTo(Timestamp.valueOf(minSupplyTime)) > 0)
            supplyTime = minSupplyTime;

//        System.out.println("recalculate():");
//        System.out.println("barcode: " + barcode);
//        System.out.println("saleTime: " + saleTime);
//        System.out.println("supplyTime: " + supplyTime);
//        System.out.println("usedQuantity: " + usedQuantity);
//        System.out.println("\n");

        List<Sale> sales = saleDao.getSalesFromTime(barcode, saleTime);
        List<Supply> supplies = supplyDao.getSuppliesFromTime(barcode, supplyTime);

        int currentSupplyIndex = 0;
        long currentUsedQuantity = usedQuantity;
        Supply currentSupply = currentSupplyIndex < supplies.size() ? supplies.get(currentSupplyIndex) : null;

        for (Sale sale : sales) {

            PrefixSumsByDate prevPrefixSums = prefixSumsSalesDao.getLastByDate(
                    sale.getTime().toString(),
                    sale.getBarcode(),
                    true
            );

            long quantitySum = (prevPrefixSums == null ? 0L : prevPrefixSums.getQuantitySum()) + sale.getQuantity();
            long revenueSum = (prevPrefixSums == null ? 0L : prevPrefixSums.getRevenueSum()) + sale.getQuantity() * sale.getPrice();
            long netProfitSum = (prevPrefixSums == null ? 0L : prevPrefixSums.getNetProfitSum()) + sale.getQuantity()  * sale.getPrice();

            cursor = cursorDao.getBySaleTime(sale.getBarcode(), sale.getTime().toString());

            if (cursor == null)
                throw new IllegalStateException("There is no cursor for this sale, but has to be");

            cursor.setQuantityUsed(currentUsedQuantity);
            cursor.setSupplyTime(currentSupply != null ? currentSupply.getTime() : null);
            cursorDao.set(cursor);

            long left = sale.getQuantity();
            while (left > 0 && currentSupply != null && sale.getTime().compareTo(currentSupply.getTime()) > -1) {
                long leftInSupply = currentSupply.getQuantity() - currentUsedQuantity;

                netProfitSum -= Math.min(left, leftInSupply) * currentSupply.getPrice();

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

            BarcodeCursor barcodeCursor = cursorDao.getByBarcode(sale.getBarcode());
            if (barcodeCursor == null)
                throw new IllegalStateException("There is no barcode cursor for this sale, but has to be");
            barcodeCursor.setQuantityUsed(currentUsedQuantity);
            barcodeCursor.setSupplyTime(currentSupply != null ? currentSupply.getTime() : null);
            cursorDao.set(barcodeCursor);

            PrefixSumsByDate prefixSums = prefixSumsSalesDao.getByDate(sale.getTime().toString(), sale.getBarcode());
            if (prefixSums == null)
                throw new IllegalStateException("There is no prefix sum according to this sale, but has to be");
            prefixSums.setQuantitySum(quantitySum);
            prefixSums.setRevenueSum(revenueSum);
            prefixSums.setNetProfitSum(netProfitSum);
            prefixSumsSalesDao.set(prefixSums);
        }

    }

}
