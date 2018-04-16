package com.lyl.eureka;

public class RemoteServiceFallBack implements RemoteService {

    @Override
    public String call() {
        return "request message";
    }

    @Override
    public int insert() {
        return 0;
    }
}
