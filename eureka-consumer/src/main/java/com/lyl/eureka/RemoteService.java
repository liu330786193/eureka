package com.lyl.eureka;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@FeignClient(value = "eureka-producer", fallback = RemoteServiceFallBack.class)
public interface RemoteService {

    @RequestMapping("/call/2")
    String call();

    @RequestMapping("/insert")
    int insert();

}
