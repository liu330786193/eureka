package com.lyl.eureka;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {

    private static final Logger LOGGER = LogManager.getLogger(ProducerService.class);

//    @Trace
    public Producer getProducer(Integer id){
        LOGGER.info("百里放羊");
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
