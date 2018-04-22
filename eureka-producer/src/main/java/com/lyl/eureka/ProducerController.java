package com.lyl.eureka;

import com.lyl.eureka.entity.Account;
import com.lyl.eureka.service.AccountService;
import com.tsign.cat.plugin.toolkit.trace.activation.annotation.TraceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProducerController {

    @Autowired
    private ProducerService producerService;

    @Autowired
    private AccountService accountService;

    @GetMapping(value = "/call/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Producer call(@PathVariable Integer id){
        System.out.println("traceId:" + TraceContext.traceId());
        return producerService.getProducer(id);
    }

    @GetMapping(value = "/insert", produces = MediaType.APPLICATION_JSON_VALUE)
    int insert(){
        Account account = new Account();
        account.setAge(11);
        account.setUsername("lyl");
        account.setPassword("123456");
        return accountService.insert(account);
    }

}
