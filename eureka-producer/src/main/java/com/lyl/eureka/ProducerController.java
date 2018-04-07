package com.lyl.eureka;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerController {

    @GetMapping(value = "/call/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Producer call(@PathVariable Integer id){
        Producer producer = new Producer();
        producer.setId(id);
        producer.setName("blfy");
        return producer;
    }
}
