package com.hdsx.dubbo.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.hdsx.dubbo.order")
public class OrderApplicationTest {

	public static void main(String[] args) {
	    SpringApplication.run(OrderApplicationTest.class, args);
	}

}