package com.aviator.content_servive.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleResponseDTO {
    public UUID id;

    public String title;

    public String slug;

    public String summary;

    public String content;

    public String status;

    public UUID categoryId;

    public UUID authorId;

    public String bannerUrl;

    public String youtubeLink;

    public String pdfUrl;

    public LocalDateTime publishedAt;

    public LocalDateTime createdAt;

    public LocalDateTime updatedAt;
}
