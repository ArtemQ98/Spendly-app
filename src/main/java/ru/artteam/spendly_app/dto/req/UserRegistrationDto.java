package ru.artteam.spendly_app.dto.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegistrationDto {
    @NotBlank(message = "The email cannot be empty.")
    @Email(message = "Incorrect Email")
    public String email;

    @NotBlank(message = "The password cannot be empty")
    @Size(min = 6, message = "The password must be at least 6 characters long")
    private String password;

    private String username;
}
