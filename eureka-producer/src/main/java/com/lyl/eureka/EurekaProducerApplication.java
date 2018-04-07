package com.lyl.eureka;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EurekaProducerApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(EurekaProducerApplication.class).web(true).run(args);
	}
}
