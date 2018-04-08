package com.lyl.eureka;

public class RemoteServiceFallBack implements RemoteService {

    @Override
    public String call() {
        return "request message";
    }
}
