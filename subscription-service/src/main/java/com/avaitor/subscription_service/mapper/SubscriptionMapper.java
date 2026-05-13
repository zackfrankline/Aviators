package com.avaitor.subscription_service.mapper;

import com.avaitor.subscription_service.dto.CategoryIdResponseDTO;
import com.avaitor.subscription_service.model.Subscription;

public class SubscriptionMapper {
    public static CategoryIdResponseDTO toCategoryIdResponseDTO(Subscription subscription){
        return CategoryIdResponseDTO.builder()
                .categoryId(subscription.getSubscriptionId().getCategoryId().toString())
                .build();
    }
}
