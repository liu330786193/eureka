package com.lyl.eureka;

import com.lyl.eureka.plugin.springmvc.annotation.TraceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//@Configuration
@RestController
public class ConsumerController {

    @Autowired
    private HelloService helloService;

//    @Autowired
//    private RemoteService remoteService;

   /* @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }*/


    /*@GetMapping("/router")
    @ResponseBody
    public String router(){
        RestTemplate tpl = getRestTemplate();
        String json = tpl.getForObject("http://eureka-producer/call/2", String.class);
        return json;
    }*/

    @GetMapping("/feign")
    @ResponseBody
    public String feign(){
        System.out.println("traceId:" + TraceContext.traceId());
        return helloService.hello();
    }


}
