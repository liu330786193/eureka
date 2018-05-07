package com.lyl.eureka;

public class RemoteServiceFallBack implements RemoteService {

    @Override
    public String call() {
        return "request message";
    }

    @Override
    public int insert() {
        System.out.println("insert方法报错");
        return 0;
    }

    @Override
    public int errorA() {
        System.out.println("errorA方法报错");
        return 0;
    }

    @Override
    public int errorB() {
        System.out.println("errorB方法报错");
        return 0;
    }

    @Override
    public int errorAB() {
        System.out.println("errorAB方法报错");
        return 0;
    }
}
