package com.aviator.content_service.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "articles")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public UUID id;

    @Column(unique = true, nullable = false)
    public String title;

    @NotNull(message = "slug is required")
    @NotBlank(message = "slug is required")
    @Column(unique = true, nullable = false)
    public String slug;

    public String summary;
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "content", columnDefinition = "jsonb", nullable = false)
    public Map<String,Object> content;

    @Column(nullable = false)
    public String status;

    @Column(name = "category_id", nullable = true)
    public UUID categoryId;

    @Column(name = "author_id", nullable = false)
    public UUID authorId;

    @Column(name = "banner_url")
    public String bannerUrl;

    @Column(name = "youtube_link")
    public String youtubeLink;

    @Column(name = "pdf_url")
    public String pdfUrl;

    @Column(name = "published_at")
    @JsonFormat(pattern = "yyyy-MM-ddTHH:mm:ss")
    public LocalDateTime publishedAt;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @JsonFormat(pattern = "yyyy-MM-ddTHH:mm:ss")
    public LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @JsonFormat(pattern = "yyyy-MM-ddTHH:mm:ss")
    public LocalDateTime updatedAt;
}
