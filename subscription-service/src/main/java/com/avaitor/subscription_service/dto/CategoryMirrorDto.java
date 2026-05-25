package com.avaitor.subscription_service.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryMirrorDto {
    public String Id;
    public String name;
    public String slug;
    public String createdAt;
}
