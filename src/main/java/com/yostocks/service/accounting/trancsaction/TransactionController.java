package com.yostocks.service.accounting.trancsaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

@RequestMapping("/transaction")
@RestController
public class TransactionController {

    private TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {

        this.transactionService = transactionService;
    }


    @Async
    @PostMapping("/register")
    public CompletableFuture addTransaction(@Valid @RequestBody TransactionModel t){

        TransactionModel savedTransactionModel = transactionService.registerTransaction(t);
        return CompletableFuture.completedFuture(savedTransactionModel);
    }
}
