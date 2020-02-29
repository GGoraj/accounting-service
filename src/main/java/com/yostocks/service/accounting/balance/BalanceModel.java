package com.yostocks.service.accounting.balance;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "balances")
public class BalanceModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "user_id", unique = true)
    @NotNull
    private Long user_id;

    @Column(name = "balance")
    @NotNull
    @Min(0)
    private double balance;



    public BalanceModel(Long id, Long user_id, double balance) {

        this.id = id;
        this.user_id = user_id;
        this.balance = balance;
    }

    public BalanceModel(Long user_id, double balance) {
        this.user_id = user_id;
        this.balance = balance;
    }

    public BalanceModel() {
        //
    }


}
