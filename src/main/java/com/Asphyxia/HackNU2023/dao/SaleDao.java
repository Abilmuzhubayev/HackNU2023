package com.Asphyxia.HackNU2023.dao;

import com.Asphyxia.HackNU2023.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface SaleDao {

    List<Sale> getSalesByPeriod(String barcode, String from, String to);
}
