package com.yostocks.service.accounting.trancsaction;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor

@Entity
@Table(name = "transactions")
public class TransactionModel {

    @Id
    // https://stackoverflow.com/questions/10041938/how-to-choose-the-id-generation-strategy-when-using-jpa-and-hibernate
    //GenerationType.Identity tells POSTGRES to Auto Generate ids  of type Long
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @NotNull
    private Long user_id;

    @NotNull
    private Long fraction_id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TransactionType transaction_type;

    @NotNull
    private double amount;

    @NotNull
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Timestamp time_stamp;


    public TransactionModel(Long user_id, Long fraction_id,
                            TransactionType transaction_type,
                            double amount, Timestamp  time_stamp) {

        this.user_id = user_id;
        this.fraction_id = fraction_id;
        this.transaction_type = transaction_type;
        this.amount = amount;
        this.time_stamp =  time_stamp;
    }

    public TransactionModel() {
        //empty
    }
}
