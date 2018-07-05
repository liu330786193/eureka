package com.lyl.eureka;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EurekaZuulAApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(EurekaZuulAApplication.class).web(true).run(args);
	}
}
