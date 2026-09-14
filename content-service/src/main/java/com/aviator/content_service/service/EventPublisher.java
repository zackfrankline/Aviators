package com.aviator.content_service.service;

import com.aviator.content_service.dto.CategoryEvent;

public interface EventPublisher {
    public void sendEventMessage(CategoryEvent categoryEvent);
}
