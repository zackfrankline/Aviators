package com.aviator.jwt_security.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    @NotNull
    @NotBlank(message = "Username should not be empty")
    private String userName;
    @NotNull
    @NotNull
    @NotBlank(message = "Password should not be empty")
    private String password;
}
