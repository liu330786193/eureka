package com.lyl.eureka;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Configuration
@Controller
public class ConsumerController {

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    @GetMapping("/router")
    @ResponseBody
    public String router(){
        RestTemplate tpl = getRestTemplate();
        String json = tpl.getForObject("http://eureka-producer/call/2", String.class);
        return json;
    }
}
