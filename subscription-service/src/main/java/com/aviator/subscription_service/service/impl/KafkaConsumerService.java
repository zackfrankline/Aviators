package com.avaitor.subscription_service.service.impl;

import com.avaitor.subscription_service.dto.CategoryEvent;
import com.avaitor.subscription_service.service.CategoryMirrorService;
import com.avaitor.subscription_service.service.EventConsumerService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService implements EventConsumerService {

    private final CategoryMirrorService categoryMirrorService;

    public KafkaConsumerService(CategoryMirrorService categoryMirrorService){
        this.categoryMirrorService = categoryMirrorService;
    }

    @KafkaListener(topics = "category-events", groupId = "subscription-service-group")
    @Override
    public void listen(CategoryEvent categoryEvent) {
        if("UPSERT".equals(categoryEvent.getEventType())){
            categoryMirrorService.upsertCategory(categoryEvent);
        }
        else if("DELETE".equals(categoryEvent.getEventType())){
            categoryMirrorService.deleteCategory(categoryEvent);
        }
    }
}
