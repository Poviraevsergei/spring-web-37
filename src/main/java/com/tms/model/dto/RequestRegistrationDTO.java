package com.tms.model.dto;

import com.tms.annotation.IsAdult;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.context.annotation.Scope;

@Scope("prototype")
@Data
public class RequestRegistrationDTO {
    @NotNull
    @Size(min = 2, max = 10)
    @NotBlank
    private String username;

    @Pattern(regexp = "[A-z0-9]{6,}")
    private String password;

    @Size(min = 2, max = 10)
    @NotNull
    private String firstName;

    @Size(min = 2, max = 10)
    @NotNull
    private String lastName;

    @Size(min = 5, max = 30)
    @Email
    private String email;

    @IsAdult
    private Integer age;
}