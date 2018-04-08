package com.lyl.eureka;

import com.lyl.eureka.plugin.springmvc.annotation.Trace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelloService {

    @Autowired RemoteService remoteService;

    @Trace
    public String hello(){
        printHello();
        remoteService.call();
        return call1();
    }

    @Trace
    public void printHello(){
        System.out.println("say hello");
    }


    @Trace
    public String call1(){
        return remoteService.call();
    }

}
