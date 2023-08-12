package com.tel_ran.rent_company;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableTransactionManagement
@ComponentScan
@EnableSwagger2
public class RentCompanyApplication {
    //TODO: add docker
    //TODO: check the initial plain java implementation and test endpoints in swagger
    public static void main(String[] args) {
        SpringApplication.run(RentCompanyApplication.class, args);
    }

}
