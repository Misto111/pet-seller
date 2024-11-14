package com.project.petSeller.model.dto;
import com.project.petSeller.model.validation.FieldMatch;
import com.project.petSeller.model.validation.UniqueUserEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@FieldMatch(
        first = "password",
        second = "confirmPassword",
        message = "Password should match"
)
public record UserRegistrationDTO(@NotEmpty String firstName,
                                  @NotEmpty String lastName,
                                  @NotBlank(message = "Email is required.") @Email(message = "Email should be valid.") @UniqueUserEmail(message = "Email already exists.") String email,
                                  String password,
                                  String confirmPassword) {

    public String fullName() {
        return firstName + " " + lastName;
    }

    public static UserRegistrationDTO emptyUserRegistrationDTO() {
        return new UserRegistrationDTO(null, null, null, null, null);
    }

}

