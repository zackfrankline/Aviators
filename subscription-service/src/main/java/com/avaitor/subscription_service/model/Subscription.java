package com.avaitor.subscription_service.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@Table(name = "subscriptions")
public class Subscription {
    @EmbeddedId
    private SubscriptionId subscriptionId;

    @CreationTimestamp
    @Column(name = "subscribed_at", nullable = false, updatable = false)
    @JsonFormat(pattern = "yyyy-MM-ddTHH:mm:ss")
    private LocalDateTime subscribedAt;

    public Subscription(UUID userId, UUID categoryId){
        this.subscriptionId = new SubscriptionId(userId, categoryId);
    }
}
