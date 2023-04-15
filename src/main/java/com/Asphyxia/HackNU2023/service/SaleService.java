package com.Asphyxia.HackNU2023.service;

import com.Asphyxia.HackNU2023.dao.SaleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaleService {

    @Autowired
    private SaleDao saleDao;

}
