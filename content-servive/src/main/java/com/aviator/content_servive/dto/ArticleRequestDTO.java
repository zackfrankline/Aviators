package com.aviator.content_servive.dto;


import com.aviator.content_servive.dto.customValidation.UpdateReadArticles;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleRequestDTO {

    @NotBlank(message = "Title is required")
    public String title;

    @NotBlank(message = "slug is required")
    public String slug;

    public String summary;

    @NotBlank(message = "Content cannot be Empty")
    public String content;

    // Matches exactly "open", "working", or "published"
    @NotBlank(message = "Status cannot be empty")
    @Pattern(regexp = "^(Open|Working|Published)$", message = "Status must be 'Open', 'Working', or 'Published'")
    public String status;

    @NotNull(message = "Category Id cannot be null")
    public UUID categoryId;

    //can be null while creating article
    @NotNull(groups = {UpdateReadArticles.class} , message = "Author ID cannot be Empty")
    public UUID authorId;

    public String bannerUrl;

    public String youtubeLink;

    public String pdfUrl;

}
