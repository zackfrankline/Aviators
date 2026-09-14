package com.aviator.content_service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CategoryEvent {
    public String id;
    public String title;
    public String slug;
    public String eventType; //upsert , delete
}
