package com.avaitor.subscription_service.service.Impl;

import com.avaitor.subscription_service.dto.CategoryEvent;
import com.avaitor.subscription_service.service.CategoryMirrorService;
import com.avaitor.subscription_service.service.EventConsumerService;
import jakarta.annotation.PostConstruct;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService implements EventConsumerService {

    private final CategoryMirrorService categoryMirrorService;

    @PostConstruct
    public void initCheck() {
        System.out.println("====================================================");
        System.out.println("CRITICAL RUNTIME TEST: KafkaConsumerService Bean Initialized!");
        System.out.println("====================================================");
    }

    public KafkaConsumerService(CategoryMirrorService categoryMirrorService){
        this.categoryMirrorService = categoryMirrorService;
    }

    @KafkaListener(topics = "category-events", groupId = "subscription-service-group")
    @Override
    public void listen(CategoryEvent categoryEvent) {
        System.out.println("Recieved Event from kafka: " + categoryEvent.getTitle() );

        if("UPSERT".equals(categoryEvent.getEventType())){
            categoryMirrorService.upsertCategory(categoryEvent);
        }
        else if("DELETE".equals(categoryEvent.getEventType())){
            categoryMirrorService.deleteCategory(categoryEvent);
        }
    }
}
