package com.tms.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Schema(description = "Используем для создания пользователей в системе(не регистрация)")
@Data
public class UserCreateDto {
    @NotBlank
    @Schema(description = "Имя пользователя", example = "Bob")
    private String firstName;
    @NotBlank
    private String lastName;
    @Email
    private String email;
    @Min(18)
    @Max(100)
    private Integer age;
}
