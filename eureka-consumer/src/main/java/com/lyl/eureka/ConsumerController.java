package com.lyl.eureka;

import com.tsign.cat.plugin.tookit.trace.activation.annotation.TraceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Configuration
@RestController
public class ConsumerController {

    @Autowired
    private HelloService helloService;

//    @Autowired
//    private RemoteService remoteService;

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }


    @GetMapping("/call")
    @ResponseBody
    public String router(){
        RestTemplate tpl = getRestTemplate();
        String json = tpl.getForObject("http://eureka-producer/call/2", String.class);
        return json;
    }

    @GetMapping("/insert")
    @ResponseBody
    public String insert(){
        RestTemplate tpl = getRestTemplate();
        String json = tpl.getForObject("http://eureka-producer/insert", String.class);
        return json;
    }

    @GetMapping("/feign")
    @ResponseBody
    public String feign(){
        System.out.println("traceId:" + TraceContext.traceId());
        return helloService.hello();
    }


}
