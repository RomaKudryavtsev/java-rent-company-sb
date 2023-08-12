package com.tel_ran.rent_company;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan
@EnableTransactionManagement
public class RentCompanyApplication {
	//TODO: add pagination
	//TODO: add docker
	public static void main(String[] args) {
		SpringApplication.run(RentCompanyApplication.class, args);
	}

}
