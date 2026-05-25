package com.avaitor.subscription_service.service;

import com.avaitor.subscription_service.dto.CategoryEvent;

public interface EventConsumerService {
    public void listen(CategoryEvent categoryEvent);
}
