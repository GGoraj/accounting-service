package com.yostocks.service.accounting.balance;

import com.yostocks.service.accounting.balance.model.GetBalanceResponseModel;
import com.yostocks.service.accounting.balance.model.InitialiseUserCashBalanceRequest;
import com.yostocks.service.accounting.balance.model.SimpleResponseModel;
import com.yostocks.service.accounting.trancsaction.TransactionType;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.Calendar;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BalanceControllerTests {

    private RestTemplate restTemplate;

    @BeforeAll
    void createRestTemplate() {
        restTemplate = new RestTemplate();
    }

    @Test
    @Order(1)
    void initializeBalance() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        InitialiseUserCashBalanceRequest requestModel = new InitialiseUserCashBalanceRequest(1L, 10000);
        HttpEntity<InitialiseUserCashBalanceRequest> entity = new HttpEntity<>(requestModel, headers);

        ResponseEntity<SimpleResponseModel> response = restTemplate.postForEntity("http://localhost:8092/balance/init", entity, SimpleResponseModel.class);

        Assert.assertTrue(response.getStatusCode().equals(HttpStatus.ACCEPTED));
        Assert.assertTrue(response.getBody().getResponse().equals("initialized"));

    }

    @Test
    @Order(2)
    void getBalance() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        GetBalanceResponseModel response = restTemplate.getForObject("http://localhost:8092/balance/" + 1, GetBalanceResponseModel.class);
        Assert.assertTrue(response.getResponse() == 10000);

    }

    // PURCHASE
    @Test
    @Order(3)
    void updateBalance_Purchase() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        Timestamp timestamp = new java.sql.Timestamp(System.currentTimeMillis());
        System.out.println("**********************" + timestamp.toString());

        UpdateBalanceRequestModel requestModel = new UpdateBalanceRequestModel(1L, 1L, TransactionType.PURCHASE, 500, timestamp);
        HttpEntity<UpdateBalanceRequestModel> entity = new HttpEntity<>(requestModel, headers);

        ResponseEntity<UpdateBalanceRequestModel> response = restTemplate.postForEntity("http://localhost:8092/balance/update", entity, UpdateBalanceRequestModel.class);
        Assert.assertTrue(response.getStatusCode().equals(HttpStatus.ACCEPTED));
    }

    // SALE
    @Test
    @Order(4)
    void updateBalance_Sale() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Timestamp timestamp = new java.sql.Timestamp(System.currentTimeMillis());


        UpdateBalanceRequestModel requestModel = new UpdateBalanceRequestModel(1L, 1L, TransactionType.SALE, 500, timestamp);
        HttpEntity<UpdateBalanceRequestModel> entity = new HttpEntity<>(requestModel, headers);

        ResponseEntity<UpdateBalanceRequestModel> response = restTemplate.postForEntity("http://localhost:8092/balance/update", entity, UpdateBalanceRequestModel.class);
        Assert.assertTrue(response.getStatusCode().equals(HttpStatus.ACCEPTED));
    }

}
