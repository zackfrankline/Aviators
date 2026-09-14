package com.aviator.subscription_service.service;

import com.aviator.subscription_service.dto.CategoryEvent;

public interface EventConsumerService {
    public void listen(CategoryEvent categoryEvent);
}
