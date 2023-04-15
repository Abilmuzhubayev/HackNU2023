package com.Asphyxia.HackNU2023.dao;

import com.Asphyxia.HackNU2023.entity.Supply;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SupplyDaoI implements SupplyDao {

    @Autowired
    private EntityManager em;

    @Override
    public List<Supply> getSuppliesByPeriod(String barcode, String to) {
        String sql = "select * from supply where barcode = ?1 and supply_time < ?2";
        Query query = em.createNativeQuery(sql);
        query.setParameter(1, barcode);
        query.setParameter(2, to);
        return (List<Supply>) query.getResultList();
    }

}
