package com.snzn.project.stock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class GenericStockServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GenericStockServiceApplication.class, args);
    }

}
