package com.Asphyxia.HackNU2023.dao;

import com.Asphyxia.HackNU2023.dto.SaleDto;
import com.Asphyxia.HackNU2023.entity.Sale;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SaleDaoI implements SaleDao {

    @Autowired
    private EntityManager em;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Sale> getSalesByPeriod(BigInteger barcode, String to) {
        String sql = "select * from sale where barcode = ?1 and sale_time <= ?2 order by sale_time ASC";
        Query query = em.createNativeQuery(sql, Sale.class);
        query.setParameter(1, barcode);
        query.setParameter(2, to);
        return (List<Sale>) query.getResultList();
    }

    @Override
    public List<SaleDto> getSalesByPeriod(BigInteger barcode, String from, String to) {
        String sql = "";
        if (barcode != null && from != null && to != null) {
            sql = "select * from sale where barcode = ?1 and (sale_time between ?2 and ?3)";
        } else if (barcode == null && from != null && to != null) {
            sql = "select * from sale where sale_time between ?2 and ?3";
        } else if (barcode == null && from != null && to == null) {
            sql = "select * from sale where sale_time >= ?2";
        } else if (barcode == null && from == null && to != null) {
            sql = "select * from sale where sale_time <= ?3";
        } else if (barcode != null && from != null && to == null) {
            sql = "select * from sale where barcode = ?1 and sale_time >= ?2";
        } else if (barcode != null && from == null && to == null) {
            sql = "select * from sale where barcode = ?1";
        } else if (barcode != null && from == null && to != null) {
            sql = "select * from sale where barcode = ?1 and sale_time <= ?3";
        } else if (barcode == null && from == null && to == null) {
            sql = "select * from sale";
        }

        Query query = em.createNativeQuery(sql, Sale.class);
        if (barcode != null)
            query.setParameter(1, barcode);
        if (from != null)
            query.setParameter(2, from);
        if (to != null)
            query.setParameter(3 ,to);

        List<Sale> sales =  query.getResultList();
        List<SaleDto> result = new ArrayList<>();
        for (Sale sale : sales) {
            SaleDto saleDto = convertToDto(sale);
            result.add(saleDto);
        }
        return result;
    }

    @Override
    public List<Sale> getSalesFromTime(BigInteger barcode, String from) {
        String sql = "select * from sale where barcode = ?1 and sale_time >= ?2 order by sale_time ASC";
        Query query = em.createNativeQuery(sql, Sale.class);
        query.setParameter(1, barcode);
        query.setParameter(2, from);
        return (List<Sale>) query.getResultList();
    }

    @Override
    public void deleteSale(Long id) {
        Sale sale = em.find(Sale.class, id);
        em.remove(sale);
    }

    @Override
    public void editSale(SaleDto saleDto) {
        Sale sale = convertToEntity(saleDto);
        em.merge(sale);
    }

    private Sale convertToEntity(SaleDto saleDto) {
        Sale sale = modelMapper.map(saleDto, Sale.class);
        return sale;
    }

    private SaleDto convertToDto(Sale sale) {
        SaleDto saleDto = modelMapper.map(sale, SaleDto.class);
        return saleDto;
    }

    public Long addSale(SaleDto saleDto) {
        Sale sale = convertToEntity(saleDto);
        em.persist(sale);
        em.flush();
        return sale.getId();
    }

    public SaleDto getSaleById(Long id) {
        Sale sale = em.find(Sale.class, id);
        return convertToDto(sale);
    }

}
