package com.aviator.content_servive.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class ArticleFilterDto {
    public String search;
    public String status;
    public String categoryId;

    @JsonFormat(pattern = "yyyy-MM-ddTHH:mm:ss")
    public String publishedAfter;

    @JsonFormat(pattern = "yyyy-MM-ddTHH:mm:ss")
    public String publishedBefore;
}
