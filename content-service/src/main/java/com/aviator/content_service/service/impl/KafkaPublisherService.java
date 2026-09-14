package com.aviator.content_servive.service.impl;

import com.aviator.content_servive.dto.CategoryEvent;
import com.aviator.content_servive.service.EventPublisher;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaPublisherService implements EventPublisher {

    private final KafkaTemplate<String, CategoryEvent> kafkaTemplate;
    private static final String TOPIC = "category-events";

    public KafkaPublisherService(KafkaTemplate<String, CategoryEvent> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendEventMessage(CategoryEvent categoryEvent) {
        kafkaTemplate.send(TOPIC, categoryEvent.getId(), categoryEvent);
    }
}
