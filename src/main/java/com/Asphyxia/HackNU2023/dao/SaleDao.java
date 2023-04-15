package com.Asphyxia.HackNU2023.dao;

import com.Asphyxia.HackNU2023.dto.SaleDto;
import com.Asphyxia.HackNU2023.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

public interface SaleDao {

    List<Sale> getSalesByPeriod(BigInteger barcode, String to);

    List<SaleDto> getSalesByPeriod(BigInteger barcode, String from, String to);

    List<Sale> getSalesFromTime(BigInteger barcode, String from);

    void deleteSale(Long sale);

    void editSale(SaleDto saleDto);

    Long addSale(SaleDto saleDto);

    SaleDto getSaleById(Long id);

}
