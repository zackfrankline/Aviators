package com.aviator.content_servive.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseDTO {
    public UUID Id;
    public String name;
    public String slug;
    public String description;
    public String createdAt;
}
