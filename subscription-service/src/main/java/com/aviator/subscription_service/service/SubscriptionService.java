package com.aviator.subscription_service.service;

import com.aviator.subscription_service.dto.CategoryIdResponseDTO;
import com.aviator.subscription_service.dto.SubscriptionCountResponseDTO;

import java.util.List;


public interface SubscriptionService {
    public List<CategoryIdResponseDTO> getAllSubsribedCategoriesForUser(String userId);
    public SubscriptionCountResponseDTO getSubscriptionCountPerCategory(String userId);
    public String subscribeCategory(String categoryId);
    public String unSubscribeCategory(String categoryId);

}
