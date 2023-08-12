package com.tel_ran.rent_company;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableTransactionManagement
@EnableSwagger2
public class RentCompanyApplication {

	public static void main(String[] args) {
		SpringApplication.run(RentCompanyApplication.class, args);
	}

}
