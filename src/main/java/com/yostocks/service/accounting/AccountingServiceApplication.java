package com.yostocks.service.accounting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executor;

@EnableAsync
@EnableEurekaClient
@SpringBootApplication
public class AccountingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountingServiceApplication.class, args);
    }


    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(75);
        executor.setMaxPoolSize(75);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("Accounting-");
        executor.setAllowCoreThreadTimeOut(true);
        executor.initialize();
        return executor;
    }

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

}
