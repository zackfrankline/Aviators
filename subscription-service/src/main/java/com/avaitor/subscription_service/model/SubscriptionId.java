package com.avaitor.subscription_service.model;

import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
@Builder
@Setter
@Getter
@NoArgsConstructor
public class SubscriptionId implements Serializable {
    private UUID userId;
    private UUID categoryId;

    public SubscriptionId(UUID userId, UUID categoryId){
        this.userId = userId;
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || o.getClass() != this.getClass()) return false;
        SubscriptionId that = (SubscriptionId) o;
        return Objects.equals(that.userId, userId) && Objects.equals(that.categoryId, categoryId);
    }

    @Override
    public int hashCode(){
        return Objects.hash(userId, categoryId);
    }
}
