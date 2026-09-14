package com.aviator.subscription_service.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryEvent {
    public String id;
    public String title;
    public String slug;
    public String eventType; //UPSERT , DELETE
}
