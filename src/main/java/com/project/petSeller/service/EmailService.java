package com.project.petSeller.service;

public interface EmailService {

    void sendRegistrationEmail(String userEmail,
                               String userName,
                               String activationCode);
}
