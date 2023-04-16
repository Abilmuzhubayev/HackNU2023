package com.Asphyxia.HackNU2023.dao;

import com.Asphyxia.HackNU2023.entity.BarcodeCursor;
import com.Asphyxia.HackNU2023.entity.SaleToSupplyCursor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public class CursorDaoI implements CursorsDao {

    @Autowired
    private EntityManager em;

    @Override
    public SaleToSupplyCursor getBySaleTime(BigInteger barcode, String saleTime) {
        String sql = "select * from sale_to_supply_cursor where barcode = ?1 and sale_time = ?2";
        Query query = em.createNativeQuery(sql, SaleToSupplyCursor.class);
        query.setParameter(1, barcode);
        query.setParameter(2, saleTime);
        try {
            return (SaleToSupplyCursor) query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println(e);
            return null;
        }
    }

    @Override
    public SaleToSupplyCursor getNextByTime(BigInteger barcode, String saleTime) {
        String sql = "select * from sale_to_supply_cursor where barcode = ?1 and sale_time >= ?2 order by sale_time asc limit 1";
        Query query = em.createNativeQuery(sql, SaleToSupplyCursor.class);
        query.setParameter(1, barcode);
        query.setParameter(2, saleTime);

        try {
            return (SaleToSupplyCursor) query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println(e);
            return null;
        }
    }

    @Override
    public SaleToSupplyCursor getPrevByTime(BigInteger barcode, String supplyTime) {
        String sql = "select * from sale_to_supply_cursor where barcode = ?1 and supply_time <= ?2 order by supply_time desc limit 1";
        Query query = em.createNativeQuery(sql, SaleToSupplyCursor.class);
        query.setParameter(1, barcode);
        query.setParameter(2, supplyTime);

        try {
            return (SaleToSupplyCursor) query.getSingleResult();
        } catch (NoResultException e) {
            sql = "select * from sale_to_supply_cursor where barcode = ?1 order by supply_time asc limit 1";
            query = em.createNativeQuery(sql, SaleToSupplyCursor.class);
            query.setParameter(1, barcode);

            return null;
        }

    }

    @Override
    public void set(SaleToSupplyCursor cursor) {
        em.merge(cursor);
    }

    @Override
    public void delete(SaleToSupplyCursor cursor) {
        em.remove(cursor);
    }

    @Override
    public BarcodeCursor getByBarcode(BigInteger barcode) {
        String sql = "select * from barcode_cursor where barcode = ?1";
        Query query = em.createNativeQuery(sql, BarcodeCursor.class);
        query.setParameter(1, barcode);
        try {
            return (BarcodeCursor) query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println(e);
            return null;
        }
    }

    @Override
    public void set(BarcodeCursor cursor) {
        em.merge(cursor);
    }

    @Override
    public void delete(BarcodeCursor cursor) {
        em.remove(cursor);
    }

}
