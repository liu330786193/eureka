package com.lyl.eureka;

import com.lyl.eureka.plugin.springmvc.annotation.TraceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerController {

    @Autowired
    private ProducerService producerService;

    @GetMapping(value = "/call/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Producer call(@PathVariable Integer id){
        System.out.println("traceId:" + TraceContext.traceId());
        return producerService.getProducer(id);
    }
}
