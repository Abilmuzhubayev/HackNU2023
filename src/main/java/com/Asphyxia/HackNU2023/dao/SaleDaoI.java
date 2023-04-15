package com.Asphyxia.HackNU2023.dao;

import com.Asphyxia.HackNU2023.entity.Sale;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Repository
public class SaleDaoI implements SaleDao {

    @Autowired
    private EntityManager em;

    @Override
    public List<Sale> getSalesByPeriod(BigInteger barcode, String to) {
        String sql = "select * from sale where barcode = ?1 and (sale_time <= ?2) order by sale_time ASC";
        Query query = em.createNativeQuery(sql, Sale.class);
        query.setParameter(1, barcode);
        query.setParameter(2, to);
        return (List<Sale>) query.getResultList();
    }
}
