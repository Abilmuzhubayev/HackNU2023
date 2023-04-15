package com.Asphyxia.HackNU2023.dao;

import com.Asphyxia.HackNU2023.entity.Supply;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplyDao {

    List<Supply> getSuppliesByPeriod(String barcode, String to);
}

