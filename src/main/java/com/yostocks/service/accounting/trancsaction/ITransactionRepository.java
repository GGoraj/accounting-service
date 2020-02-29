package com.yostocks.service.accounting.trancsaction;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.Collection;

public interface ITransactionRepository extends CrudRepository<TransactionModel, Long> {


    @Query(value = "select sum(t.amount) from transactions t where t.fraction_id= :fraction_id and t.transaction_type= :transaction_type", nativeQuery = true)
    Double getSumOfTransactionAmounts(Long fraction_id, String transaction_type);


    // all transactions by fraction_id
    @Query(value = "select * from transactions t where t.fraction_id = :fraction_id", nativeQuery = true)
    Collection<TransactionModel> findAllByFractionId(Long fraction_id);

    // all transactions by fraction_id And transaction_type
    @Query(value = "select * from transactions t where t.fraction_id = :fraction_id and t.transaction_type = :transaction_type;", nativeQuery = true)
    Collection<TransactionModel> findAllByFractionIdAndTransactionType(Long fraction_id, TransactionType transaction_type);

    // all transactions by user_id
    @Query(value = "select * from transactions t where t.user_id = :user_id and t.transaction_type = :transaction_type", nativeQuery = true)
    Collection<TransactionModel> findAllByUserId(Long user_id, TransactionType transaction_type);

    // select user's first investment in particular stock
    @Query(value = "select MIN(t.time_stamp) from transactions t where t.user_id = :user_id and t.fraction_id = :fraction_id;", nativeQuery = true)
    TransactionModel findFirstInvestmentByUserIdAndFractionId(Long user_id, Long fraction_id);



}