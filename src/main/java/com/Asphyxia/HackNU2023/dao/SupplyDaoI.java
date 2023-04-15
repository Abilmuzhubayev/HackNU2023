package com.Asphyxia.HackNU2023.dao;

import com.Asphyxia.HackNU2023.dto.SaleDto;
import com.Asphyxia.HackNU2023.dto.SupplyDto;
import com.Asphyxia.HackNU2023.entity.Sale;
import com.Asphyxia.HackNU2023.entity.Supply;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SupplyDaoI implements SupplyDao {

    @Autowired
    private EntityManager em;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Supply> getSuppliesByPeriod(BigInteger barcode, String to) {
        String sql = "select * from supply where barcode = ?1 and supply_time <= ?2 order by supply_time ASC";
        Query query = em.createNativeQuery(sql, Supply.class);
        query.setParameter(1, barcode);
        query.setParameter(2, to);
        return (List<Supply>) query.getResultList();
    }

    @Override
    public List<SupplyDto> getSuppliesByPeriod(BigInteger barcode, String from, String to) {
        String sql = "select * from supply where barcode = ?1 and (supply_time between ?1 and ?2)";
        Query query = em.createNativeQuery(sql, Supply.class);
        query.setParameter(1, barcode);
        query.setParameter(2, from);
        query.setParameter(2, to);
        List<Supply> supplies = query.getResultList();

        List<SupplyDto> result = new ArrayList<>();

        for (Supply supply : supplies) {
            SupplyDto supplyDto = convertToDto(supply);
            result.add(supplyDto);
        }
        return result;
    }

    @Override
    public List<Supply> getSuppliesFromTime(BigInteger barcode, String from) {
        String sql = "select * from supply where barcode = ?1 and supply_time >= ?2 order by supply_time ASC";
        Query query = em.createNativeQuery(sql, Supply.class);
        query.setParameter(1, barcode);
        query.setParameter(2, from);
        return (List<Supply>) query.getResultList();
    }

    @Override
    public void deleteSupply(Long id) {
        Supply supply = em.find(Supply.class, id);
        em.remove(supply);
    }

    @Override
    public void editSupply(SupplyDto supplyDto) {
        Supply supply = convertToEntity(supplyDto);
        em.merge(supply);
    }

    @Override
    public Long addSupply(SupplyDto supplyDto) {
        Supply supply = convertToEntity(supplyDto);
        em.persist(supply);
        em.flush();
        return supply.getId();
    }

    @Override
    public SupplyDto getSupplyById(Long id) {
        Supply supply = em.find(Supply.class, id);
        return convertToDto(supply);
    }


    private Supply convertToEntity(SupplyDto supplyDto) {
        Supply supply = modelMapper.map(supplyDto, Supply.class);
        return supply;
    }

    private SupplyDto convertToDto(Supply supply) {
        SupplyDto supplyDto = modelMapper.map(supply, SupplyDto.class);
        return supplyDto;
    }

}
