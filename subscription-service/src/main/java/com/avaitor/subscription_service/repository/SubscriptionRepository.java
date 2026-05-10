package com.avaitor.subscription_service.repository;

import com.avaitor.subscription_service.model.Subscription;
import com.avaitor.subscription_service.model.SubscriptionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, SubscriptionId> {

}
