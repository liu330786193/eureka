package com.lyl.eureka;


import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringCloudApplication
@EnableZuulProxy
public class EurekaZuulApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(EurekaZuulApplication.class).web(true).run(args);
	}

}
