package com.project.petSeller.service.impl;

import com.project.petSeller.model.dto.UserRegistrationDTO;
import com.project.petSeller.model.entity.UserEntity;
import com.project.petSeller.model.events.UserRegisteredEvent;
import com.project.petSeller.repository.UserRepository;
import com.project.petSeller.service.UserService;
import com.project.petSeller.service.exception.InvalidPasswordException;
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

    // Конструкторът инжектира зависимостите за UserRepository, PasswordEncoder, ApplicationEventPublisher и UserDetailsService.
    public UserServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            ApplicationEventPublisher appEventPublisher,
            UserDetailsService petSellerUserDetailsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.appEventPublisher = appEventPublisher;
        this.petSellerUserDetailsService = petSellerUserDetailsService;
    }

    @Override
    public void registerUser(UserRegistrationDTO userRegistrationDTO) {

        // Валидация на парола
        if (!isValidPassword(userRegistrationDTO.password())) {
            throw new InvalidPasswordException();
        }

        // Създава нов потребител (UserEntity) чрез map метода и го записва в базата данни
        UserEntity userEntity = map(userRegistrationDTO);
        userRepository.save(userEntity);

        // Публикува събитие UserRegisteredEvent, когато потребителят е успешно регистриран
        appEventPublisher.publishEvent(new UserRegisteredEvent(
                "UserService",                    // Източник на събитието (за проследяване)
                userRegistrationDTO.email(),      // Имейл на новорегистрирания потребител
                userRegistrationDTO.fullName()    // Пълно име на потребителя
        ));
    }

    @Override
    public void createUserIfNotExist(String email, String names) {
        // Проверка дали потребителят съществува в базата данни
        if (userRepository.findByEmail(email).isEmpty()) {
            // Ако не съществува, създава нов UserEntity без парола и го записва в базата
            UserEntity userEntity = new UserEntity()
                    .setEmail(email)
                    .setFirstName(names.split(" ")[0]) // Взима първото име от names
                    .setLastName(names.split(" ")[1])  // Взима фамилията от names
                    .setActive(true);                  // Задава, че акаунтът е активен
            userRepository.save(userEntity);
        }
    }

    @Override
    public Authentication login(String email) {
        // Зарежда потребителските данни по имейл, чрез UserDetailsService
        UserDetails userDetails = petSellerUserDetailsService.loadUserByUsername(email);

        // Създава Authentication обект с потребителските данни за текущата сесия
        Authentication auth = new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );

        // Задава Authentication обекта в SecurityContext, за да се счита потребителят за логнат
        SecurityContextHolder.getContext().setAuthentication(auth);

        // Връща Authentication обекта, ако има нужда от него в други части на кода
        return auth;
    }

    // Частен метод, който преобразува UserRegistrationDTO в UserEntity
    private UserEntity map(UserRegistrationDTO userRegistrationDTO) {
        // Създава и връща нов UserEntity с информация от UserRegistrationDTO и криптирана парола
        return new UserEntity()
                .setActive(false)                              // Потребителят е неактивен по подразбиране
                .setFirstName(userRegistrationDTO.firstName()) // Взема първото име от DTO
                .setLastName(userRegistrationDTO.lastName())   // Взема фамилията от DTO
                .setEmail(userRegistrationDTO.email())         // Задава имейл от DTO
                .setPassword(passwordEncoder.encode(userRegistrationDTO.password())); // Кодира паролата и я задава
    }


    // Валидация на парола (например минимална дължина)
    private boolean isValidPassword(String password) {
        return password.length() >= 5; // Минимум 5 символа
    }
}
