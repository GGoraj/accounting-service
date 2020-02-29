package com.yostocks.service.accounting.balance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InitBalanceRequestModel {

    @NotNull
    private Long user_id;

    @NotNull
    private double amount;
}
