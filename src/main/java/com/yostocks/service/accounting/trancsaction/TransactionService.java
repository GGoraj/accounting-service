package com.yostocks.service.accounting.trancsaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private ITransactionRepository transactionRepo;


    @Autowired
    public TransactionService(ITransactionRepository transactionRepo) {
        this.transactionRepo = transactionRepo;
    }


    public TransactionModel registerTransaction(TransactionModel t) {

        return transactionRepo.save(t);

    }

}
