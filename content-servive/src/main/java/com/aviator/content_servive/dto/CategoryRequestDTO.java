package com.aviator.content_servive.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequestDTO {
    public String Id;

    @NotBlank(message = "Name is Required")
    @Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "Name must contain only letters and numbers")
    public String name;

    public String slug;

    @NotBlank(message = "Description is Required")
    @Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "Description must contain only letters and numbers")
    public String description;
}
