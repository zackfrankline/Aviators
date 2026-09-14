package com.aviator.content_servive.service;

import com.aviator.content_servive.dto.CategoryEvent;

public interface EventPublisher {
    public void sendEventMessage(CategoryEvent categoryEvent);
}
