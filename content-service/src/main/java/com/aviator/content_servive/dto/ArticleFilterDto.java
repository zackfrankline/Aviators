package com.aviator.content_servive.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleFilterDto {
    public String search;
    public String status;  // 'Open', 'Working', 'Published'
    public String categoryId;

    @JsonFormat(pattern = "yyyy-MM-ddTHH:mm:ss")
    public String publishedAfter;

    @JsonFormat(pattern = "yyyy-MM-ddTHH:mm:ss")
    public String publishedBefore;
}
