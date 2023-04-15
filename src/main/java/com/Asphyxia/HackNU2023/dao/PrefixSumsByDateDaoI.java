package com.Asphyxia.HackNU2023.dao;

import com.Asphyxia.HackNU2023.entity.PrefixSumsByDate;
import com.Asphyxia.HackNU2023.entity.Sale;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;

public class PrefixSumsByDateDaoI implements PrefixSumsByDateDao {

    @Autowired
    private EntityManager em;

    @Override
    public PrefixSumsByDate getLastByDate(String date, BigInteger barcode, Boolean isLess) {
        String sql;
        if (isLess) {
            sql = "select * from prefix_sums_sales where barcode = ?1 and date < ?2 order by date DESC limit 1";
        } else {
            sql = "select * from prefix_sums_sales where barcode = ?1 and date <= ?2 order by date DESC limit 1";
        }
        Query query = em.createNativeQuery(sql, PrefixSumsByDate.class);
        query.setParameter(1, barcode);
        query.setParameter(2, date);
        return (PrefixSumsByDate) query.getSingleResult();
    }
}
