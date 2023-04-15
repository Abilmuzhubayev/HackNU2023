package com.Asphyxia.HackNU2023.dao;

import com.Asphyxia.HackNU2023.entity.Supply;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;


public interface SupplyDao {

    List<Supply> getSuppliesByPeriod(BigInteger barcode, String to);

    List<Supply> getSuppliesFromTime(BigInteger barcode, String from);

}

