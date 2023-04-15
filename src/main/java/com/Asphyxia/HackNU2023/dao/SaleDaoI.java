package com.Asphyxia.HackNU2023.dao;

import com.Asphyxia.HackNU2023.entity.Sale;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SaleDaoI implements SaleDao {

    @Autowired
    private EntityManager em;

    @Override
    public List<Sale> getSalesByPeriod(String barcode, String from, String to) {
        String sql = "select * from sale where barcode = ?1 and (sale_time between ?2 and ?3)";
        Query query = em.createNativeQuery(sql);
        query.setParameter(1, barcode);
        query.setParameter(2, from);
        query.setParameter(3, to);
        return (List<Sale>) query.getResultList();
    }
}
