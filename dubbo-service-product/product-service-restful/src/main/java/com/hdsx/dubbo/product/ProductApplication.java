package com.hdsx.dubbo.product;

import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.apache.dubbo.config.spring.context.annotation.EnableDubboConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableDubboConfig
@DubboComponentScan("com.hdsx.dubbo.product.controller")
@SpringBootApplication
public class ProductApplication {

	public static void main(String[] args) {
	    SpringApplication.run(ProductApplication.class, args);
	}

}
