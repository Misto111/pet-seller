package com.project.petSeller.service;
import com.project.petSeller.model.dto.UserRegistrationDTO;
import org.springframework.security.core.Authentication;

public interface UserService {

    void registerUser(UserRegistrationDTO userRegistrationDTO);

    Authentication login(String email);

    void createUserIfNotExist(String email, String name);

}
