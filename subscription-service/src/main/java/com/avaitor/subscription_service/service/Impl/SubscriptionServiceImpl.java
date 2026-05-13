package com.avaitor.subscription_service.service.Impl;

import com.avaitor.subscription_service.dto.CategoryIdResponseDTO;
import com.avaitor.subscription_service.dto.SubscriptionCountResponseDTO;
import com.avaitor.subscription_service.exception.DuplicateSubscriptionRequestException;
import com.avaitor.subscription_service.mapper.SubscriptionMapper;
import com.avaitor.subscription_service.model.Subscription;
import com.avaitor.subscription_service.model.SubscriptionId;
import com.avaitor.subscription_service.repository.SubscriptionRepository;
import com.avaitor.subscription_service.security.SecurityUtility;
import com.avaitor.subscription_service.service.SubscriptionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final SecurityUtility securityUtility;
    /**
     * @param userId
     * @return CategoryIdResponseDTO
     */
    @Override
    public List<CategoryIdResponseDTO> getAllSubsribedCategoriesForUser(String userId) {
        List<Subscription> subscriptions = subscriptionRepository.findBySubscriptionId_UserId(UUID.fromString(userId));
        return subscriptions.stream()
                .map(SubscriptionMapper::toCategoryIdResponseDTO)
                .toList();
    }

    /**
     * @param categoryId
     * @return SubscriptionCountResponseDTO
     */
    @Override
    public SubscriptionCountResponseDTO getSubscriptionCountPerCategory(String categoryId) {
        long count = subscriptionRepository.countBySubscriptionId_CategoryId(UUID.fromString(categoryId));
        return SubscriptionCountResponseDTO.builder()
                .categoryId(categoryId)
                .userCount(String.valueOf(count))
                .build();
    }

    /**
     * @param category
     * @return
     */
    @Override
    @Transactional
    public String subscribeCategory(String categoryId) {
        UUID userId = securityUtility.getCurrentUserId();
        UUID categoryIdUUID = UUID.fromString(categoryId);

        if(!securityUtility.isCurrentUserAudience()){
            throw new IllegalArgumentException("You're are not Authorised to Subscribe/unSubscribe");
        }
        if(subscriptionRepository.existsBySubscriptionId_CategoryIdAndSubscriptionId_UserId(categoryIdUUID, userId)){
            throw new DuplicateSubscriptionRequestException("Subscription Already Exists");
        }
        SubscriptionId subscriptionId = new SubscriptionId(userId, categoryIdUUID);
        Subscription subscription = Subscription.builder()
                .subscriptionId(subscriptionId)
                .build();
        try{
            subscriptionRepository.save(subscription);
        }
        catch (Exception e){
            throw new IllegalArgumentException(e.getMessage());
        }
        return "Subscribed Successfully";
    }

    /**
     * @param category
     * @return
     */
    @Override
    @Transactional
    public String unSubscribeCategory(String categoryId) {
        UUID userId = securityUtility.getCurrentUserId();
        UUID categoryIdUUID = UUID.fromString(categoryId);

        if(!securityUtility.isCurrentUserAudience()){
            throw new IllegalArgumentException("You're are not Authorised to Subscribe/UnSubscribe");
        }

        //if user is subscribed then allow
        if(!subscriptionRepository.existsBySubscriptionId_CategoryIdAndSubscriptionId_UserId(userId, categoryIdUUID)){
            throw new DuplicateSubscriptionRequestException("Not subscribed to the Category");
        }

        Subscription subscription = subscriptionRepository.findBySubscriptionId_UserIdAndSubscriptionId_CategoryId(userId, categoryIdUUID);
        try{
            subscriptionRepository.delete(subscription);
        }
        catch (Exception e){
            throw new IllegalArgumentException(e.getMessage());
        }

        return "UnSubscribed Succesfully";
    }
}
