package com.yostocks.service.accounting.balance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

@Controller
@RequestMapping("/balance")
public class BalanceController {

    private BalanceService balanceService;

    @Autowired
    public BalanceController(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @Async
    @PostMapping("/init")
    public CompletableFuture initializeBalance(@Valid @RequestBody InitBalanceRequestModel request){
        String response = balanceService.initiateUserCashBalance(request.getUser_id(), request.getAmount());

        if(response.equals(null)){
            return CompletableFuture.completedFuture(new ResponseEntity<>(Collections.singletonMap("response", "balance not initalized"), HttpStatus.CONFLICT));
        }
        if(response.equals("initialized")) {

            return CompletableFuture.completedFuture(new ResponseEntity<>(Collections.singletonMap("response", response), HttpStatus.ACCEPTED));
        }
        else{
            response = "ConstraintViolationException";
            return CompletableFuture.completedFuture(new ResponseEntity<>(Collections.singletonMap("response","ConstraintViolationException, Balance can't be initialized twice"), HttpStatus.CONFLICT));
        }

    }

    /**
     *  used by 'stocks-service' to register transaction
     */
    @Async
    @PostMapping("/update")
    public CompletableFuture updateBalanceAndRegisterTransaction(@Valid @RequestBody UpdateBalanceRequestModel request){

        BalanceModel balance = balanceService.updateBalanceAndRegisterTransaction(request.getUser_id(), request.getFraction_id(),
                                                            request.getTransaction_type(), request.getAmount(),
                                                             request.getTimestamp());

        return CompletableFuture.completedFuture(new ResponseEntity<>(balance, HttpStatus.ACCEPTED));

    }

    @Async
    @GetMapping("/{user_id}")
    public CompletableFuture getBalance(@NotNull @PathVariable("user_id") Long user_id){

        double balance = balanceService.getBalance(user_id);
        if(balance == -1){
            return CompletableFuture.completedFuture(new ResponseEntity<>(Collections.singletonMap("response", "balance not initialized"),HttpStatus.OK));
        }

        return CompletableFuture.completedFuture(new ResponseEntity<>(Collections.singletonMap("response", String.valueOf(balance)),HttpStatus.OK));

    }
}