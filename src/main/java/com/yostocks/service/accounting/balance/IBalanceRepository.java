package com.yostocks.service.accounting.balance;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


public interface IBalanceRepository extends CrudRepository<BalanceModel, Long> {

    @Query(value="select * from balances b where b.user_id = ?1", nativeQuery = true)
    BalanceModel findByUserId(Long user_id);

}
