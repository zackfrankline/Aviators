package com.aviator.subscription_service.mapper;

import com.aviator.subscription_service.dto.CategoryIdResponseDTO;
import com.aviator.subscription_service.model.Subscription;

public class SubscriptionMapper {
    public static CategoryIdResponseDTO toCategoryIdResponseDTO(Subscription subscription){
        return CategoryIdResponseDTO.builder()
                .categoryId(subscription.getSubscriptionId().getCategoryId().toString())
                .build();
    }
}
