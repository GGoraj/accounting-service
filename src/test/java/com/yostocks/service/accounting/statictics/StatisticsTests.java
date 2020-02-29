package com.yostocks.service.accounting.statictics;

import com.yostocks.service.accounting.statistics.StatisticsService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StatisticsTests {


    StatisticsService statisticsService;

    @Before
    @Autowired
    public void init(StatisticsService statisticsService){
        this.statisticsService = statisticsService;

        // create few fractions
    }

    @Test
    void s(){
        statisticsService.getPercentRoiOfSingleFraction(1L);
    }

}
