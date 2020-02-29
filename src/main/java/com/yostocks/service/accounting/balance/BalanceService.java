package com.yostocks.service.accounting.balance;

import com.yostocks.service.accounting.trancsaction.TransactionModel;
import com.yostocks.service.accounting.trancsaction.TransactionService;
import com.yostocks.service.accounting.trancsaction.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Service
public class BalanceService {

    private IBalanceRepository balanceRepo;

    private TransactionService transactionService;

    @Autowired
    public BalanceService(IBalanceRepository balanceRepo, TransactionService transactionService) {
        this.balanceRepo = balanceRepo;
        this.transactionService = transactionService;
    }

    public String initiateUserCashBalance(Long user_id, double amount) {

        BalanceModel balanceToBe = new BalanceModel(user_id, amount);

        try {
            balanceRepo.save(balanceToBe);
        } catch (DataIntegrityViolationException e) {
            return e.getLocalizedMessage();
        }

        return "initialized";
    }

    public double getBalance(Long user_id) {
        double balance = -1;
        try{
            balance = balanceRepo.findByUserId(user_id).getBalance();
        }
        catch(NullPointerException e){
            return balance;
        }
        return balance;
    }


    public BalanceModel updateBalanceAndRegisterTransaction(Long user_id, Long fraction_id, TransactionType transaction_type,
                                                            double amount, Timestamp timestamp) {

        BigDecimal balanceFromDb = null;
        BalanceModel balance = balanceRepo.findByUserId(user_id);
        try {
            balanceFromDb = BigDecimal.valueOf(balance.getBalance());
        }
        catch (NullPointerException ex){
            return null;
        }
        BigDecimal amountBigDecimal = BigDecimal.valueOf(amount);

        // Purchase:
        if (transaction_type.equals(TransactionType.PURCHASE)) {

            // update balance
            balanceFromDb = balanceFromDb.subtract(amountBigDecimal);
            balance.setBalance(balanceFromDb.doubleValue());

            // register transaction
            registerTransactionAdapter(user_id, fraction_id, transaction_type, amount, timestamp);

            return balanceRepo.save(balance);

        }
        // Sale:
        else {
            // update balance
            balanceFromDb = balanceFromDb.add(amountBigDecimal);
            balance.setBalance(balanceFromDb.doubleValue());

            // register transaction
            registerTransactionAdapter(user_id, fraction_id, transaction_type, amount, timestamp);

            return balanceRepo.save(balance);
        }
    }

    private void registerTransactionAdapter(Long user_id, Long fraction_id, TransactionType transaction_type, double amount, Timestamp timestamp) {
        // register transaction
        TransactionModel transaction = new TransactionModel(user_id, fraction_id, transaction_type, amount, timestamp);

        transactionService.registerTransaction(transaction);
    }


}
