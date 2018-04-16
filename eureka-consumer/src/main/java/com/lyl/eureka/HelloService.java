package com.lyl.eureka;

import com.tsign.cat.plugin.tookit.trace.activation.annotation.Trace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelloService {

    @Autowired
    RemoteService remoteService;

    @Trace
    public String hello(){
        printHello();
//        remoteService.call();
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

    @Trace
    public int insert(){
        return remoteService.insert();
    }


}
