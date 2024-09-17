package com.eagledev.timemanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class TimeManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(TimeManagementApplication.class, args);
    }

}
