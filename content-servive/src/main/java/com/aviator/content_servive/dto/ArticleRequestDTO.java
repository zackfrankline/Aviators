package com.aviator.content_servive.dto;


import com.aviator.content_servive.dto.customValidation.UpdateArticleValidation;
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
    @NotNull(groups = UpdateArticleValidation.class, message = "Id cannot be null")
    @NotBlank(groups = UpdateArticleValidation.class, message = "Id cannot be blank")
    public String Id;

    @NotBlank(message = "Title is required")
    public String title;

    public String slug;

    public String summary;

    @NotBlank(message = "Content cannot be Empty")
    public String content;

    // Matches exactly "open", "working", or "published"
    @NotBlank(message = "Status cannot be empty")
    @Pattern(regexp = "^(Open|Working|Published)$", message = "Status must be 'Open', 'Working', or 'Published'")
    public String status;

    @NotNull(message = "Category Id cannot be null")
    public String categoryId;

    //add validation
    public String bannerUrl;

    //add validation for youtube links
    public String youtubeLink;

    //add validation for pdfUrl
    public String pdfUrl;

}
