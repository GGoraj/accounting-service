package com.yostocks.service.accounting.statistics;

import com.yostocks.service.accounting.trancsaction.ITransactionRepository;
import com.yostocks.service.accounting.trancsaction.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
public class StatisticsService {

    private RestTemplate restTemplate;
    private ITransactionRepository transactionRepository;

    @Autowired
    public StatisticsService(RestTemplate restTemplate, ITransactionRepository transactionRepository) {
        this.restTemplate = restTemplate;
        this.transactionRepository = transactionRepository;
    }


    public Double getCashRoiOnSingleFraction(Long fraction_id){

        HttpHeaders headers = configureHeaders();
        Double response = restTemplate.getForObject("http://stocks-service/fraction/gain/single/" + fraction_id, Double.class, headers);


        Double sumOfAllPurchasesDb = transactionRepository.getSumOfTransactionAmounts(fraction_id, "PURCHASE");
        Double sumOfAllSalesDb = transactionRepository.getSumOfTransactionAmounts(fraction_id, "SALE");

        // if there was no purchase - there is no gain
        if(sumOfAllPurchasesDb == null){
            return null;
        }
        // it's ok if there was no sales yet, it means user keeps his stock for now
        if(sumOfAllSalesDb == null){
            sumOfAllSalesDb = 0.0;
        }


        BigDecimal gain =  BigDecimal.valueOf(response);
        BigDecimal sumOfAllPurchasesBD = BigDecimal.valueOf(sumOfAllPurchasesDb);
        BigDecimal sumOfAllSalesBD = BigDecimal.valueOf(sumOfAllSalesDb);

        BigDecimal cashRoi = gain.subtract(sumOfAllPurchasesBD.subtract(sumOfAllSalesBD));

        return cashRoi.doubleValue();


    }


    public Double getPercentRoiOfSingleFraction(Long fraction_id) {

       /* //get single stock percent
        HttpHeaders headers = configureHeaders();
        ResponseEntity<FractionPercentResponseModel> response = restTemplate.getForEntity("http://stocks-service/fraction/percent/" + fraction_id,  FractionPercentResponseModel.class);
        // extract value from response entity
        double fractionPercent = response.getBody().getResponse();*/

       Double cashGainOnSingleFraction = getCashRoiOnSingleFraction(fraction_id);

       // if there is no cash gain, there is no ROI
       if(cashGainOnSingleFraction == null){
           return null;
       }
       //get PURCHASE
       Double summedPurchases =  transactionRepository.getSumOfTransactionAmounts(fraction_id, TransactionType.PURCHASE.toString());
       Double summedSales = transactionRepository.getSumOfTransactionAmounts(fraction_id, TransactionType.SALE.toString());

       // if no fractions purchased
       if(summedPurchases == null){
           return null;
       }
       if(summedSales == null){
           summedSales = 0.0;
       }
       // prepare for calculations
       BigDecimal purchasesBD = BigDecimal.valueOf(summedPurchases);
       BigDecimal salesBD = BigDecimal.valueOf(summedSales);
       BigDecimal cashGainBD = BigDecimal.valueOf(cashGainOnSingleFraction);
       // calculate total cost of investment
       BigDecimal costsBD = purchasesBD.subtract(salesBD);
       BigDecimal percentRoi = (cashGainBD.subtract(costsBD)).divide(costsBD);

       return percentRoi.doubleValue();

    }




    public void getPercentRoiOfAllFractions() {
        //get single stock fraction data from stock-service
        HttpHeaders headers = configureHeaders();

    }

    private HttpHeaders configureHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
