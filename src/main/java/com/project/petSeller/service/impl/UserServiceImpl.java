package com.project.petSeller.service.impl;


import com.project.petSeller.model.dto.UserRegistrationDTO;
import com.project.petSeller.model.entity.UserEntity;
import com.project.petSeller.model.events.UserRegisteredEvent;
import com.project.petSeller.repository.UserRepository;
import com.project.petSeller.service.UserService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  private final ApplicationEventPublisher appEventPublisher;
  private final UserDetailsService petSellerUserDetailsService;

  public UserServiceImpl(
      UserRepository userRepository,
      PasswordEncoder passwordEncoder,
      ApplicationEventPublisher appEventPublisher,
      UserDetailsService userDetailsService) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.appEventPublisher = appEventPublisher;
    this.petSellerUserDetailsService = userDetailsService;
  }

  @Override
  public void registerUser(
      UserRegistrationDTO userRegistrationDTO) {

    userRepository.save(map(userRegistrationDTO));

    appEventPublisher.publishEvent(new UserRegisteredEvent(
        "UserService",
        userRegistrationDTO.email(),
        userRegistrationDTO.fullName()
    ));
  }

  @Override
  public void createUserIfNotExist(String email, String names) {
    // Create manually a user in the database
    // password not necessary
  }

  @Override
  public Authentication login(String email) {
    UserDetails userDetails = petSellerUserDetailsService.loadUserByUsername(email);

    Authentication auth = new UsernamePasswordAuthenticationToken(
        userDetails,
        userDetails.getPassword(),
        userDetails.getAuthorities()
    );

    SecurityContextHolder.getContext().setAuthentication(auth);

    return auth;
  }

  private UserEntity map(UserRegistrationDTO userRegistrationDTO) {
    return new UserEntity()
        .setActive(false)
        .setFirstName(userRegistrationDTO.firstName())
        .setLastName(userRegistrationDTO.lastName())
        .setEmail(userRegistrationDTO.email())
        .setPassword(passwordEncoder.encode(userRegistrationDTO.password()));
  }


}
