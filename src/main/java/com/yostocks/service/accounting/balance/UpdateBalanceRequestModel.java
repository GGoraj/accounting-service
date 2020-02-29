package com.yostocks.service.accounting.balance;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yostocks.service.accounting.trancsaction.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBalanceRequestModel {

    @NotNull
    private Long user_id;

    @NotNull
    private Long fraction_id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TransactionType transaction_type;

    @NotNull
    private double amount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSS")
    @NotNull Timestamp timestamp;


}
