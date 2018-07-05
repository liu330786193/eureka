package com.lyl.eureka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ZuulController {

    @Autowired
    private DiscoveryClient client;

    @RequestMapping(value = "/add/{a}/{b}",method = RequestMethod.GET)
    public String add(@PathVariable Integer a, @PathVariable Integer b)
    {
        System.out.println("host:"+client.getLocalServiceInstance().getHost()+"   -----port:"+client.getLocalServiceInstance().getPort());
        return a+b+" ------      host:"+client.getLocalServiceInstance().getHost()+"   -----port:"+client.getLocalServiceInstance().getPort();
    }

}
