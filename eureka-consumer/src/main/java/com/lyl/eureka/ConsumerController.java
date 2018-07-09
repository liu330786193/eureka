package com.lyl.eureka;

import com.timevale.cat.toolkit.trace.Trace;
import com.timevale.cat.toolkit.trace.TraceContext;
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
    public int insert(){
        return helloService.insert();
    }

    @GetMapping("/feign/errorA")
    @ResponseBody
    public int errorA(){
        return helloService.errorA();
    }

    @GetMapping("/feign/errorB")
    @ResponseBody
    public int errorB(){
        return helloService.errorB();
    }

    @GetMapping("/feign/errorAB")
    @ResponseBody
    public int errorAB(){
        return helloService.errorAB();
    }

    @GetMapping("/feign")
    @ResponseBody
    public String feign(){
        System.out.println("traceId:" + TraceContext.traceId());
        return helloService.hello();
    }

    @Trace
    @GetMapping("test")
    public void test(){
        System.out.println("测试程序");
    }


}
