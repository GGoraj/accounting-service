package com.yostocks.service.accounting.balance.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InitialiseUserCashBalanceRequest {

    private Long user_id;
    private double amount;

}