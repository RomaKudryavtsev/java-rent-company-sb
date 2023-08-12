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
    //TODO: add pagination
    //TODO: fix sql queries
    //TODO: change -1 to 0 in RecordService
    //TODO: fix return method
    public static void main(String[] args) {
        SpringApplication.run(RentCompanyApplication.class, args);
        System.out.println("RentCompany-SB\nTo use Swagger UI press this link: http://localhost:8080/swagger-ui/");
    }

}
