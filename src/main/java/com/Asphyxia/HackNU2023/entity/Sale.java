package com.Asphyxia.HackNU2023.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
@Table(name = "sale", schema = "umag_hacknu")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String barcode;

    private Long price;

    @Column(name = "sale_time")
    private Timestamp time;
}
