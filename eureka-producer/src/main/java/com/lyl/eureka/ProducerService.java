package com.lyl.eureka;

import com.tsign.cat.plugin.tookit.trace.activation.annotation.Trace;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {

//    @Trace
    public Producer getProducer(Integer id){
        Producer producer = new Producer();
        producer.setId(id);
        producer.setName("blfy");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return producer;
    }

}
