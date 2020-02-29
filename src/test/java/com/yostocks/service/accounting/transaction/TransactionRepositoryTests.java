package com.yostocks.service.accounting.transaction;

import com.yostocks.service.accounting.balance.BalanceModel;
import com.yostocks.service.accounting.balance.BalanceService;
import com.yostocks.service.accounting.balance.IBalanceRepository;
import com.yostocks.service.accounting.trancsaction.ITransactionRepository;
import com.yostocks.service.accounting.trancsaction.TransactionType;
import org.junit.Assert;
import org.junit.Before;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
public class TransactionRepositoryTests {

    @Autowired
    ITransactionRepository transactionRepository;


    @Autowired
    BalanceService balanceService;

    @BeforeAll
    public void init(){
        balanceService.initiateUserCashBalance(1L, 100000);
    }


    @Test
    void getSumOfTransactionAmounts_PURCHASE(){

        Double value = 0.0;
        value = transactionRepository.getSumOfTransactionAmounts(1L, TransactionType.PURCHASE.toString());
        Assert.assertTrue(value!=0.0);

    }

    @Test
    void getSumOfTransactionAmounts_SALE(){
        transactionRepository.getSumOfTransactionAmounts(1L, TransactionType.SALE.toString());
    }





}
