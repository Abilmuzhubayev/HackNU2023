package com.Asphyxia.HackNU2023.dao;

import com.Asphyxia.HackNU2023.entity.BarcodeCursor;
import com.Asphyxia.HackNU2023.entity.SaleToSupplyCursor;

import java.math.BigInteger;

public interface CursorsDao {

    SaleToSupplyCursor getBySaleTime(BigInteger barcode, String saleTime);

    SaleToSupplyCursor getNextByTime(BigInteger barcode, String saleTime);

    SaleToSupplyCursor getPrevByTime(BigInteger barcode, String saleTime);

    void set(SaleToSupplyCursor cursor);

    void delete(SaleToSupplyCursor cursor);

    BarcodeCursor getByBarcode(BigInteger barcode);

    void set(BarcodeCursor cursor);

    void delete(BarcodeCursor cursor);

}
