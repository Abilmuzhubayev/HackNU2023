package com.Asphyxia.HackNU2023.dao;

import com.Asphyxia.HackNU2023.dto.SupplyDto;
import com.Asphyxia.HackNU2023.entity.Supply;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;


public interface SupplyDao {

    List<Supply> getSuppliesByPeriod(BigInteger barcode, String to);
    List<SupplyDto> getSuppliesByPeriod(BigInteger barcode, String from, String to);
    List<Supply> getSuppliesFromTime(BigInteger barcode, String from);

    void deleteSupply(Long id);

    void editSupply(SupplyDto supplyDto);

    Long addSupply(SupplyDto supplyDto);

    SupplyDto getSupplyById(Long id);

}

