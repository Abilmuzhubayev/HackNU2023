package com.Asphyxia.HackNU2023.dao;

import com.Asphyxia.HackNU2023.entity.PrefixSumsByDate;

import java.math.BigInteger;

public interface PrefixSumsByDateDao {

    PrefixSumsByDate getLastByDate(String date, BigInteger barcode, Boolean isLess);

}
