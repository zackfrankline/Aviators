package com.avaitor.subscription_service.service;

import com.avaitor.subscription_service.dto.CategoryIdResponseDTO;
import com.avaitor.subscription_service.dto.SubscriptionCountResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;


public interface SubscriptionService {
    public List<CategoryIdResponseDTO> getAllSubsribedCategoriesForUser(String userId);
    public SubscriptionCountResponseDTO getSubscriptionCountPerCategory(String userId);
    public String subscribeCategory(String categoryId);
    public String unSubscribeCategory(String categoryId);

}
