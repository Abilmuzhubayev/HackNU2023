package com.Asphyxia.HackNU2023.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigInteger;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "supply", schema = "umag_hacknu", indexes = @Index(name = "supply_indx", columnList = "barcode, supply_time"))
public class Supply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigInteger barcode;

    private Long price;

    private Long quantity;

    @Column(name = "supply_time")
    private Timestamp time;
}
