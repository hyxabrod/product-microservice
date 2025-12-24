package com.example.product.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.example.contracts.products.ProductEvent;


@Component
public class ProductEventsProducer {

    private final KafkaTemplate<Integer, ProductEvent> kafkaTemplate;

    public ProductEventsProducer(KafkaTemplate<Integer, ProductEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(ProductEvent event) {
        if (event == null || event.getId() == null) {
            return;
        }
        kafkaTemplate.send("products.changelog", event.getId(), event);
    }
}
