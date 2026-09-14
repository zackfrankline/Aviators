package com.avaitor.subscription_service.repository;

import com.avaitor.subscription_service.model.Subscription;
import com.avaitor.subscription_service.model.SubscriptionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, SubscriptionId> {
    List<Subscription> findBySubscriptionId_UserId(UUID userId);

    long countBySubscriptionId_CategoryId(UUID categoryId);

    boolean existsBySubscriptionId_CategoryIdAndSubscriptionId_UserId(UUID categoryId, UUID userId);

    Subscription findBySubscriptionId_UserIdAndSubscriptionId_CategoryId(UUID userId, UUID categoryId);

}
