package com.aviator.jwt_security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminRegisterRequest {
    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, max = 50, message = "Name Size should be more than 3 and less than 50 characters")
    private String name;

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, max = 50, message = "Username Size should be more than 3 and less than 50 characters")
    private String userName;


    @NotBlank(message = "Email Cannot be Empty")
    @Email(message = "Email should be Valid Email")
    private String email;

    @NotBlank(message = "Password Key is required")
    private String passwordHash;

    @NotBlank(message = "Secret Key is required")
    private String secretKey;

}