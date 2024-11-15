package com.project.petSeller.service;

import com.project.petSeller.model.events.UserRegisteredEvent;

public interface UserActivationService {

    void userRegistered(UserRegisteredEvent event);

    String createActivationCode(String userEmail);
}
