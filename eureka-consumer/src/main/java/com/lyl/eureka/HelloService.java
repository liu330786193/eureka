package com.lyl.eureka;

import com.timevale.cat.toolkit.trace.Trace;
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

    @Trace
    public int errorA(){
        testException();
        return remoteService.errorA();
    }

    @Trace
    public int errorB(){
        return remoteService.errorB();
    }

    @Trace
    public int errorAB(){
        testException();
        return remoteService.errorAB();
    }

    @Trace
    public void testException(){
        int i = 10 / 0;
    }

}
