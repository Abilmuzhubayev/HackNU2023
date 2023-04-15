package com.Asphyxia.HackNU2023.dao;

import com.Asphyxia.HackNU2023.entity.Supply;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public class SupplyDaoI implements SupplyDao {

    @Autowired
    private EntityManager em;

    @Override
    public List<Supply> getSuppliesByPeriod(BigInteger barcode, String to) {
        String sql = "select * from supply where barcode = ?1 and supply_time <= ?2 order by supply_time ASC";
        Query query = em.createNativeQuery(sql, Supply.class);
        query.setParameter(1, barcode);
        query.setParameter(2, to);
        return (List<Supply>) query.getResultList();
    }

    @Override
    public List<Supply> getSuppliesFromTime(BigInteger barcode, String from) {
        String sql = "select * from supply where barcode = ?1 and supply_time >= ?2 order by supply_time ASC";
        Query query = em.createNativeQuery(sql, Supply.class);
        query.setParameter(1, barcode);
        query.setParameter(2, from);
        return (List<Supply>) query.getResultList();
    }

}
