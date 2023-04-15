package com.Asphyxia.HackNU2023.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigInteger;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "prefix_sum_by_date")
public class PrefixSumsByDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigInteger barcode;

    private Long quantitySum;

    private Long revenueSum;

    private Long netProfitSum;

    private Timestamp time;

}
