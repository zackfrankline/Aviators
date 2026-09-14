package com.avaitor.subscription_service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class SubscriptionCountResponseDTO {
    public String userCount;
    public String categoryId;
}
