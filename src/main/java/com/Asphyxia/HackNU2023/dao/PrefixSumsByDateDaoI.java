package com.Asphyxia.HackNU2023.dao;

import com.Asphyxia.HackNU2023.entity.PrefixSumsByDate;
import com.Asphyxia.HackNU2023.entity.Sale;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public class PrefixSumsByDateDaoI implements PrefixSumsByDateDao {

    @Autowired
    private EntityManager em;

    @Override
    public PrefixSumsByDate getLastByDate(String date, BigInteger barcode, Boolean isLess) {
        String sql;
        if (isLess) {
            sql = "select * from prefix_sum_by_date where barcode = ?1 and time < ?2 order by time DESC limit 1";
        } else {
            sql = "select * from prefix_sum_by_date where barcode = ?1 and time <= ?2 order by time DESC limit 1";
        }
        Query query = em.createNativeQuery(sql, PrefixSumsByDate.class);
        query.setParameter(1, barcode);
        query.setParameter(2, date);

        try {
            return (PrefixSumsByDate) query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println(e);
            return null;
        }
    }

    @Override
    public PrefixSumsByDate getByDate(String date, BigInteger barcode) {
        String sql = "select * from prefix_sum_by_date where barcode = ?1 and time = ?2";
        Query query = em.createNativeQuery(sql, PrefixSumsByDate.class);
        query.setParameter(1, barcode);
        query.setParameter(2, date);

        try {
            return (PrefixSumsByDate) query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println(e);
            return null;
        }
    }

    @Override
    public void set(PrefixSumsByDate prefixSumsByDate) {
        em.merge(prefixSumsByDate);
    }

    @Override
    public void delete(PrefixSumsByDate prefixSumsByDate) {
        em.remove(prefixSumsByDate);
    }
}
