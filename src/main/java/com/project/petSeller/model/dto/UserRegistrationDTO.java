package com.project.petSeller.model.dto;

import com.project.petSeller.model.validation.FieldMatch;
import com.project.petSeller.model.validation.UniqueUserEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
@FieldMatch(
        first = "password",
        second = "confirmPassword",
        message = "Password should match"
)
public record UserRegistrationDTO(@NotEmpty String firstName,
                                  @NotEmpty String lastName,
                                  @NotNull @Email @UniqueUserEmail String email,
                                   String password,
                                  String confirmPassword) {
    public String fullName() {
        return firstName + " " + lastName;
    }

}

